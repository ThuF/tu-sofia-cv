package tu.sofia.cv.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.commons.io.IOUtils;
import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.util.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.common.DestinationUtils;
import tu.sofia.cv.common.ICommonConstants;
import tu.sofia.cv.common.OAuthUtils;
import tu.sofia.cv.entity.Company;
import tu.sofia.cv.entity.PersonalInfo;
import tu.sofia.cv.entity.Position;
import tu.sofia.cv.entity.Project;
import tu.sofia.cv.entity.additional.GitHubAccount;
import tu.sofia.cv.entity.additional.GitHubRepo;
import tu.sofia.cv.entity.additional.LinkedInAccount;
import tu.sofia.cv.entity.additional.LinkedInPositions;
import tu.sofia.cv.entity.additional.LinkedInPositions.LinkedInPositionsWrapper.LinkedInPosition;

/**
 * Service for authentication
 */
@Singleton
@Path("protected/admin/auth")
public class AuthService {

	private PersonalInfoService personalInfoService;
	private ProjectService projectService;
	private PositionService positionService;
	private CompanyService companyService;
	private OAuthUtils oaAuthUtils;
	private DestinationUtils destinationUtils;

	/**
	 * Constructor
	 *
	 * @param personalInfoService
	 * @param projectService
	 * @param positionService
	 * @param companyService
	 * @param oaAuthUtils
	 * @param destinationUtils
	 */
	@Inject
	public AuthService(PersonalInfoService personalInfoService, ProjectService projectService, PositionService positionService,
			CompanyService companyService, OAuthUtils oaAuthUtils, DestinationUtils destinationUtils) {
		this.personalInfoService = personalInfoService;
		this.projectService = projectService;
		this.positionService = positionService;
		this.companyService = companyService;
		this.oaAuthUtils = oaAuthUtils;
		this.destinationUtils = destinationUtils;
	}

	/**
	 * LinkedIn authentication
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GET
	@Path("/linkedin")
	public void authenticateLinkedIn(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
		response.sendRedirect(oaAuthUtils.authenticate(request, ICommonConstants.OAuthProviders.Names.LINKEDIN,
				ICommonConstants.Destinations.Names.OAUTH_LINKEDIN));
	}

	/**
	 * GitHub authentication
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GET
	@Path("/github")
	public void authenticateGitHub(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
		response.sendRedirect(
				oaAuthUtils.authenticate(request, ICommonConstants.OAuthProviders.Names.GITHUB, ICommonConstants.Destinations.Names.OAUTH_GITHUB));
	}

	/**
	 * LinkedIn authentication callback
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GET
	@Path("/linkedin/callback")
	public void callbackLinkedin(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
		AuthProvider provider = oaAuthUtils.getAuthProvider(request);

		String linkedInApi = destinationUtils.getDestinationUrl(ICommonConstants.Destinations.Names.OAUTH_LINKEDIN);

		LinkedInAccount account = getLinkedInAccount(provider, linkedInApi);
		LinkedInPositions positions = getLinkedInPositions(provider, linkedInApi);

		updatePersonalInfo(account);
		addPositions(positions);
		addCompanies(positions);

		response.sendRedirect(oaAuthUtils.getSuccessAuthRedirectUrl(request));
	}

	/**
	 * GitHub authentication callback
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@GET
	@Path("/github/callback")
	public void callbackGitHub(@Context HttpServletRequest request, @Context HttpServletResponse response) throws Exception {
		AuthProvider provider = oaAuthUtils.getAuthProvider(request);

		String githubApi = destinationUtils.getDestinationUrl(ICommonConstants.Destinations.Names.OAUTH_GITHUB);
		GitHubAccount account = getGitHubAccount(provider, githubApi);
		List<GitHubRepo> repos = getGitHubRepos(provider, githubApi, account);
		addProjects(repos);
		response.sendRedirect(oaAuthUtils.getSuccessAuthRedirectUrl(request));
	}

	private LinkedInAccount getLinkedInAccount(AuthProvider provider, String linkedInApi) throws Exception, IOException {
		Response linkedInResponse = provider.api(linkedInApi + "/people/~?format=json", "GET", null, null, null);
		return new Gson().fromJson(IOUtils.toString(linkedInResponse.getInputStream()), LinkedInAccount.class);
	}

	private LinkedInPositions getLinkedInPositions(AuthProvider provider, String linkedInApi) throws Exception {
		Response linkedInResponse = provider.api(linkedInApi + "/people/~:(positions)?format=json", "GET", null, null, null);
		return new Gson().fromJson(IOUtils.toString(linkedInResponse.getInputStream()), LinkedInPositions.class);
	}

	private void addPositions(LinkedInPositions positions) {
		for (LinkedInPosition next : positions.getValues().getPositions()) {
			positionService.addPosition(new Position(next));
		}
	}

	private void addCompanies(LinkedInPositions positions) {
		for (LinkedInPosition next : positions.getValues().getPositions()) {
			if (next.getCompany() != null) {
				companyService.addCompany(new Company(next.getCompany()));
			}
		}
	}

	private void updatePersonalInfo(LinkedInAccount account) {
		PersonalInfo personalInfo = new PersonalInfo(account);
		try {
			personalInfoService.getPersonalInfo();
			personalInfoService.updatePersonalInfo(personalInfo);
		} catch (NotFoundException e) {
			personalInfoService.addPersonalInfo(personalInfo);
		}
	}

	private GitHubAccount getGitHubAccount(AuthProvider provider, String githubApi) throws Exception, IOException {
		Response githubResponse = provider.api(githubApi + "/user", "GET", null, null, null);
		return new Gson().fromJson(IOUtils.toString(githubResponse.getInputStream()), GitHubAccount.class);
	}

	private List<GitHubRepo> getGitHubRepos(AuthProvider provider, String githubApi, GitHubAccount account) throws Exception, IOException {
		Response githubResponse = provider.api(githubApi + "/users/" + account.getUser() + "/repos", "GET", null, null, null);
		return new Gson().fromJson(IOUtils.toString(githubResponse.getInputStream()), new TypeToken<ArrayList<GitHubRepo>>() {
			// GitHubRepo type
		}.getType());
	}

	private void addProjects(List<GitHubRepo> repos) {
		for (GitHubRepo next : repos) {
			projectService.add(new Project(next));
		}
	}

}

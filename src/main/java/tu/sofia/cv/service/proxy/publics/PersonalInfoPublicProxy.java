package tu.sofia.cv.service.proxy.publics;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.entity.PersonalInfo;
import tu.sofia.cv.service.PersonalInfoService;

/**
 * Public proxy for personal info
 */
@Singleton
@Path("/public/personal-info")
public class PersonalInfoPublicProxy {

	private PersonalInfoService personalInfoService;

	/**
	 * Constructor
	 * 
	 * @param personalInfoService
	 */
	@Inject
	public PersonalInfoPublicProxy(PersonalInfoService personalInfoService) {
		this.personalInfoService = personalInfoService;
	}

	/**
	 * Returns the personal info
	 *
	 * @return the personal info
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PersonalInfo getPersonalInfo() {
		return personalInfoService.getPersonalInfo();
	}
}

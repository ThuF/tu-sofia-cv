package tu.sofia.cv.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.inject.Singleton;

/**
 * Public service for checking if user is in certain role
 */
@Singleton
@Path("/public/roles")
public class RolesPublicService {

	private static final String ROLE_ADMIN = "Admin";

	/**
	 * Returns true if the currently logged in user has the "Admin" role enabled
	 * 
	 * @param request
	 * @return true if the currently logged in user has the "Admin" role enabled
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/admin")
	public Boolean isAdmin(@Context HttpServletRequest request) {
		return request.isUserInRole(ROLE_ADMIN);
	}
}

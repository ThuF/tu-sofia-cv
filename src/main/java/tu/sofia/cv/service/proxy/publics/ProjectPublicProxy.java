package tu.sofia.cv.service.proxy.publics;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.entity.Project;
import tu.sofia.cv.service.ProjectService;

/**
 * Public proxy for projects
 */
@Singleton
@Path("/public/projects")
public class ProjectPublicProxy {

	private ProjectService projectService;

	/**
	 * Constructor
	 *
	 * @param projectService
	 */
	@Inject
	public ProjectPublicProxy(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * Returns a list of all projects
	 *
	 * @return a list of all projects
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjects() {
		return projectService.getAllPublicProjects();
	}

	/**
	 * Returns the count of all projects
	 * 
	 * @return the count of all projects
	 */
	@GET
	@Path("/count")
	public Long count() {
		return projectService.count();
	}
}

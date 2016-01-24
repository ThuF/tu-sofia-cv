package tu.sofia.cv.service;

import java.text.MessageFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.common.UnitOfWorkUtils;
import tu.sofia.cv.dao.ProjectDao;
import tu.sofia.cv.entity.Project;
import tu.sofia.cv.validation.ProjectValidator;

/**
 * Service for projects
 */
@Singleton
@Path("/protected/admin/projects")
public class ProjectService extends AbstractCRUDService<Long, Project> {

	private static final String ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE = "There is no project with [projectId={0}]";

	private ProjectDao projectDao;

	/**
	 * Constructor
	 *
	 * @param projectDao
	 * @param projectValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public ProjectService(ProjectDao projectDao, ProjectValidator projectValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(projectDao, projectValidator, unitOfWorkUtils);
		this.projectDao = projectDao;
	}

	/**
	 * Returns a list of all projects
	 *
	 * @return a list of all projects
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjects() {
		return getAll();
	}

	/**
	 * Returns a list of all projects that should be displayed
	 *
	 * @return a list of all projects that should be displayed
	 */
	@GET
	@Path("/public")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getAllPublicProjects() {
		return projectDao.findAllPublicProjects();
	}

	/**
	 * Returns a project specified by the id path parameter
	 *
	 * @param id
	 * @return a project specified by the id path parameter
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Project getProject(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new project
	 *
	 * @param project
	 * @return HTTP 201 CREATED if the project was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProject(Project project) {
		return add(project);
	}

	/**
	 * Updates existing project
	 *
	 * @param id
	 * @param project
	 * @return HTTP 204 NO CONTENT if the update was successful or
	 *         HTTP 404 NOT FOUND if there was no such project
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProject(@PathParam("id") final Long id, Project project) {
		return update(id, project);
	}

	/**
	 * Deletes existing project
	 *
	 * @param id
	 * @return HTTP 204 NO CONTENT if the deletion was successful or
	 *         HTTP 404 NOT FOUND if there was no such project
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteProject(@PathParam("id") final Long id) {
		return delete(id);
	}

	/**
	 * Returns the count of all projects
	 * 
	 * @return the count of all projects
	 */
	@GET
	@Path("/count")
	public Long count() {
		return countAll();
	}

	@Override
	protected String getNotFoundMessage(Long id) {
		return MessageFormat.format(ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE, id);
	}

	@Override
	protected void updateEntityProperties(Project persistedEntity, Project entity) {
		persistedEntity.setName(entity.getName());
		persistedEntity.setDescription(entity.getDescription());
		persistedEntity.setUrl(entity.getUrl());
		persistedEntity.setIcon(entity.getIcon());
		persistedEntity.setIsPublic(entity.getIsPublic());
	}

}

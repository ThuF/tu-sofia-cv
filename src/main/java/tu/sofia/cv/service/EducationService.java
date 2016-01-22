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
import tu.sofia.cv.dao.EducationDao;
import tu.sofia.cv.entity.Education;
import tu.sofia.cv.validation.EducationValidator;

/**
 * Service for educations
 */
@Singleton
@Path("/protected/admin/educations")
public class EducationService extends AbstractCRUDService<Long, Education> {

	private static final String ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE = "There is no education with [educationId={0}]";

	/**
	 * Constructor
	 *
	 * @param educationDao
	 * @param educationValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public EducationService(EducationDao educationDao, EducationValidator educationValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(educationDao, educationValidator, unitOfWorkUtils);
	}

	/**
	 * Returns a list of all educations
	 *
	 * @return a list of all educations
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Education> getEducations() {
		return getAll();
	}

	/**
	 * Returns a education specified by the id path parameter
	 *
	 * @param id
	 * @return a education specified by the id path parameter
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Education getEducation(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new education
	 *
	 * @param education
	 * @return HTTP 201 CREATED if the education was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSkill(Education education) {
		return add(education);
	}

	/**
	 * Updates existing education
	 *
	 * @param id
	 * @param education
	 * @return HTTP 204 NO CONTENT if the update was successful or
	 *         HTTP 404 NOT FOUND if there was no such education
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEducation(@PathParam("id") final Long id, Education education) {
		return update(id, education);
	}

	/**
	 * Deletes existing education
	 *
	 * @param id
	 * @return HTTP 204 NO CONTENT if the deletion was successful or
	 *         HTTP 404 NOT FOUND if there was no such education
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteSkill(@PathParam("id") final Long id) {
		return delete(id);
	}

	@Override
	protected String getNotFoundMessage(Long id) {
		return MessageFormat.format(ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE, id);
	}

	@Override
	protected void updateEntityProperties(Education persistedEntity, Education entity) {
		persistedEntity.setDegree(entity.getDegree());
		persistedEntity.setSchoolName(entity.getSchoolName());
		persistedEntity.setFieldOfStudy(entity.getFieldOfStudy());
		persistedEntity.setStartDate(entity.getStartDate());
		persistedEntity.setEndDate(entity.getEndDate());
	}

}

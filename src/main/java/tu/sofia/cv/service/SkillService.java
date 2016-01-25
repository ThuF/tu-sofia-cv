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
import tu.sofia.cv.dao.SkillDao;
import tu.sofia.cv.entity.Skill;
import tu.sofia.cv.entity.additional.SkillType;
import tu.sofia.cv.validation.SkillValidator;

/**
 * Service for skills
 */
@Singleton
@Path("/protected/admin/skills")
public class SkillService extends AbstractCRUDService<Long, Skill> {

	private static final String ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE = "There is no skill with [skillId={0}]";

	private SkillDao skillDao;

	/**
	 * Constructor
	 *
	 * @param skillDao
	 * @param skillValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public SkillService(SkillDao skillDao, SkillValidator skillValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(skillDao, skillValidator, unitOfWorkUtils);
		this.skillDao = skillDao;
	}

	/**
	 * Returns a list of all skills
	 *
	 * @return a list of all skills
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Skill> getSkills() {
		return getAll();
	}

	/**
	 * Returns a list of skills filtered by type
	 *
	 * @param type
	 * @return a list of skills filtered by type
	 */
	@GET
	@Path("/type/{skillType}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Skill> getSkills(@PathParam("skillType") final SkillType type) {
		return skillDao.findByType(type);
	}

	/**
	 * Returns a skill specified by the id path parameter
	 *
	 * @param id
	 * @return a skill specified by the id path parameter
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Skill getSkill(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new skill
	 *
	 * @param skill
	 * @return HTTP 201 CREATED if the skill was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSkill(Skill skill) {
		return add(skill);
	}

	/**
	 * Updates existing skill
	 *
	 * @param id
	 * @param skill
	 * @return HTTP 204 NO CONTENT if the update was successful or
	 *         HTTP 404 NOT FOUND if there was no such skill
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateSkill(@PathParam("id") final Long id, Skill skill) {
		return update(id, skill);
	}

	/**
	 * Deletes existing skill
	 *
	 * @param id
	 * @return HTTP 204 NO CONTENT if the deletion was successful or
	 *         HTTP 404 NOT FOUND if there was no such skill
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteSkill(@PathParam("id") final Long id) {
		return delete(id);
	}

	/**
	 * Returns the count of all skills
	 *
	 * @return the count of all skills
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
	protected void updateEntityProperties(Skill persistedEntity, Skill entity) {
		persistedEntity.setName(entity.getName());
		persistedEntity.setType(entity.getType());
	}

}

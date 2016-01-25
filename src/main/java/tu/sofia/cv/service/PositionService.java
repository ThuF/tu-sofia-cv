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
import tu.sofia.cv.dao.PositionDao;
import tu.sofia.cv.entity.Position;
import tu.sofia.cv.validation.PositionValidator;

/**
 * Service for positions
 */
@Singleton
@Path("/protected/admin/positions")
public class PositionService extends AbstractCRUDService<Long, Position> {

	private static final String ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE = "There is no position with [positionId={0}]";

	/**
	 * Constructor
	 *
	 * @param positionDao
	 * @param positionValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public PositionService(PositionDao positionDao, PositionValidator positionValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(positionDao, positionValidator, unitOfWorkUtils);
	}

	/**
	 * Returns a list of all positions
	 *
	 * @return a list of all positions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Position> getPositions() {
		return getAll();
	}

	/**
	 * Returns a position specified by the id path parameter
	 *
	 * @param id
	 * @return a position specified by the id path parameter
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Position getPosition(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new position
	 *
	 * @param position
	 * @return HTTP 201 CREATED if the position was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPosition(Position position) {
		return add(position);
	}

	/**
	 * Updates existing position
	 *
	 * @param id
	 * @param position
	 * @return HTTP 204 NO CONTENT if the update was successful or
	 *         HTTP 404 NOT FOUND if there was no such position
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePosition(@PathParam("id") final Long id, Position position) {
		return update(id, position);
	}

	/**
	 * Deletes existing position
	 *
	 * @param id
	 * @return HTTP 204 NO CONTENT if the deletion was successful or
	 *         HTTP 404 NOT FOUND if there was no such position
	 */
	@DELETE
	@Path("/{id}")
	public Response deletePosition(@PathParam("id") final Long id) {
		return delete(id);
	}

	/**
	 * Returns the count of all positions
	 *
	 * @return the count of all positions
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
	protected void updateEntityProperties(Position persistedEntity, Position entity) {
		persistedEntity.setTitle(entity.getTitle());
		persistedEntity.setStartDate(entity.getStartDate());
		persistedEntity.setEndDate(entity.getEndDate());
		persistedEntity.setCompany(entity.getCompany());
		persistedEntity.setIcon(entity.getIcon());
	}

}

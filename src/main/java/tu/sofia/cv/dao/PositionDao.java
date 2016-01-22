package tu.sofia.cv.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.entity.Position;

/**
 * DAO class for {@link Position}
 */
public class PositionDao extends AbstractJpaDao<Long, Position> {

	private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

	protected PositionDao() {
		super(Position.class);
	}

	/**
	 * Returns a list of all positions with null end date
	 *
	 * @return a list of all positions with null end date
	 */
	public List<Position> findAllByNullEndDate() {
		TypedQuery<Position> query = getEntityManager().createNamedQuery(Position.QUERY_NAME_FIND_BY_NULL_END_DATE, Position.class);
		return query.getResultList();
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}

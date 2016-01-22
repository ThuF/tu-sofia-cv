package tu.sofia.cv.dao;

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

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

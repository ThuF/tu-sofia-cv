package tu.sofia.cv.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.entity.Education;

/**
 * DAO class for {@link Education}
 */
public class EducationDao extends AbstractJpaDao<Long, Education> {

	private static final Logger logger = LoggerFactory.getLogger(EducationDao.class);

	protected EducationDao() {
		super(Education.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

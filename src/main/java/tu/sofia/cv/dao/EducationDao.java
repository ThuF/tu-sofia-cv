package tu.sofia.cv.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.entity.Skill;

/**
 * DAO class for {@link Skill}
 */
public class EducationDao extends AbstractJpaDao<Long, Skill> {

	private static final Logger logger = LoggerFactory.getLogger(EducationDao.class);

	protected EducationDao() {
		super(Skill.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

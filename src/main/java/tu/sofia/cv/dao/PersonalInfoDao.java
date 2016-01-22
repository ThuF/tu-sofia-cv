package tu.sofia.cv.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.entity.PersonalInfo;

/**
 * DAO class for {@link PersonalInfo}
 */
public class PersonalInfoDao extends AbstractJpaDao<Long, PersonalInfo> {

	private static final Logger logger = LoggerFactory.getLogger(PersonalInfoDao.class);

	protected PersonalInfoDao() {
		super(PersonalInfo.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

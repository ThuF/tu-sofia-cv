package tu.sofia.cv.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.entity.Company;

/**
 * DAO class for {@link Company}
 */
public class CompanyDao extends AbstractJpaDao<Long, Company> {

	private static final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	protected CompanyDao() {
		super(Company.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

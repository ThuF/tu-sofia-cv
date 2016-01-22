package tu.sofia.cv.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.entity.Project;

/**
 * DAO class for {@link Project}
 */
public class ProjectDao extends AbstractJpaDao<Long, Project> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectDao.class);

	protected ProjectDao() {
		super(Project.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

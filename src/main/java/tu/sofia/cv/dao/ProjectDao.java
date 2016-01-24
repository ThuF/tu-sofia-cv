package tu.sofia.cv.dao;

import java.util.List;

import javax.persistence.TypedQuery;

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

	/**
	 * Returns a list of all public projects
	 * 
	 * @return a list of all public projects
	 */
	public List<Project> findAllPublicProjects() {
		TypedQuery<Project> query = getEntityManager().createNamedQuery(Project.QUERY_NAME_FIND_ALL_PUBLIC_PROJECTS, Project.class);
		return query.getResultList();
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
}

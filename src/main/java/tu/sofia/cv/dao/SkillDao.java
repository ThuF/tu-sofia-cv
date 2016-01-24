package tu.sofia.cv.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.entity.Skill;
import tu.sofia.cv.entity.additional.SkillType;

/**
 * DAO class for {@link Skill}
 */
public class SkillDao extends AbstractJpaDao<Long, Skill> {

	private static final Logger logger = LoggerFactory.getLogger(SkillDao.class);

	protected SkillDao() {
		super(Skill.class);
	}

	/**
	 * Returns a list of skill filtered by type
	 * 
	 * @param type
	 * @return a list of skill filtered by type
	 */
	public List<Skill> findByType(SkillType type) {
		TypedQuery<Skill> query = getEntityManager().createNamedQuery(Skill.QUERY_NAME_FIND_BY_TYPE, Skill.class);
		query.setParameter(Skill.PARAM_TYPE, type);
		return query.getResultList();
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}

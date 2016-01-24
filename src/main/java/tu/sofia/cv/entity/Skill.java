package tu.sofia.cv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import tu.sofia.cv.entity.additional.SkillType;

/**
 * Entity class representing the T_SKILL database table
 */
@Entity
@Table(name = "T_SKILL")
@NamedQueries({ @NamedQuery(name = Skill.QUERY_NAME_FIND_BY_TYPE, query = Skill.QUERY_FIND_BY_TYPE) })
public class Skill implements IJPAEntity<Long>, Serializable {

	/**
	 * The name of the query for finding all skills filtered by type
	 */
	public static final String QUERY_NAME_FIND_BY_TYPE = "findByType";

	/**
	 * The name of the type parameter
	 */
	public static final String PARAM_TYPE = "type";

	static final String QUERY_FIND_BY_TYPE = "select s from Skill s where s.type = :" + PARAM_TYPE;

	private static final long serialVersionUID = 8733337025341018485L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long skillId;

	@Column(length = 150, nullable = false)
	private String name;

	@Column(nullable = false)
	private SkillType type = SkillType.OTHER;

	/**
	 * Returns the skill Id
	 *
	 * @return the skill Id
	 */
	public Long getSkillId() {
		return skillId;
	}

	/**
	 * Sets the skill Id
	 *
	 * @param skillId
	 *            the skill Id
	 */
	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	/**
	 * Returns the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 *
	 * @param name
	 *            the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the skill type
	 *
	 * @return the skill type
	 */
	public SkillType getType() {
		return type;
	}

	/**
	 * Sets the skill type
	 *
	 * @param type
	 */
	public void setType(SkillType type) {
		this.type = type;
	}

	@Override
	public Long getKeyValue() {
		return getSkillId();
	}

	@Override
	public String toString() {
		return getName();
	}
}

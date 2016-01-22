package tu.sofia.cv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class representing the T_SKILL database table
 */
@Entity
@Table(name = "T_SKILL")
public class Skill implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = 8733337025341018485L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long skillId;

	@Column(length = 150, nullable = false)
	private String name;

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

	@Override
	public Long getKeyValue() {
		return getSkillId();
	}

}

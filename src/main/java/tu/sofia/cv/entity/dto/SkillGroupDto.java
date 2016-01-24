package tu.sofia.cv.entity.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import tu.sofia.cv.entity.Skill;
import tu.sofia.cv.entity.additional.SkillType;

/**
 * Data Transfer Object for the {@link Skill} entity
 */
public class SkillGroupDto {

	private static final String SEPARATOR = ", ";
	private String name;
	private String highlight;
	private String icon;

	/**
	 * Default empty constructor
	 */
	public SkillGroupDto() {
	}

	/**
	 * Constructor initializing all properties
	 *
	 * @param next
	 * @param skills
	 */
	public SkillGroupDto(SkillType next, List<Skill> skills) {
		setName(next.getName());
		setIcon(next.getIcon());
		setHighlight(StringUtils.join(skills.toArray(new Skill[] {}), SEPARATOR));
	}

	/**
	 * Returns the name of the skill group
	 *
	 * @return the name of the skill group
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the skill group
	 *
	 * @param name
	 *            the name of the skill group
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the highlight of the skill group
	 *
	 * @return the highlight of the skill group
	 */
	public String getHighlight() {
		return highlight;
	}

	/**
	 * Sets the highlight of the skill group
	 *
	 * @param highlight
	 *            the highlight of the skill group
	 */
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	/**
	 * Returns the icon of the skill group
	 *
	 * @return the icon of the skill group
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the icon of the skill group
	 *
	 * @param icon
	 *            the icon of the skill group
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
}

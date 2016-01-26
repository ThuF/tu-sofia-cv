package tu.sofia.cv.entity.additional;

/**
 * Enumeration of the skill types
 */
public enum SkillType {

	/**
	 * The technical skill type
	 */
	TECHNICAL_SKILL("Technical Skills", "fa fa-laptop"),

	/**
	 * The soft skill type
	 */
	SOFT_SKILL("Soft Skills", "fa fa-comments"),

	/**
	 * Others type
	 */
	OTHER("Others", "fa fa-ellipsis-h"),

	/**
	 * The hobby type
	 */
	HOBBY("Hobbies", "fa fa-futbol-o");

	private final String name;
	private final String icon;

	SkillType(String name, String icon) {
		this.name = name;
		this.icon = icon;
	}

	/**
	 * Returns the name of the skill type
	 *
	 * @return the name of the skill type
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the icon of the skill type
	 *
	 * @return the icon of the skill type
	 */
	public String getIcon() {
		return icon;
	}
}

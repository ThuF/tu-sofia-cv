package tu.sofia.cv.entity.dto;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import tu.sofia.cv.entity.Education;
import tu.sofia.cv.entity.Position;

/**
 * Data Transfer Object for the {@link Education} and {@link Position} entities
 */
public class TimelineDto {

	private String title;
	private Date startDate;
	private Date endDate;
	private String icon;
	private String description;
	private Boolean isEducation;

	/**
	 * Constructor
	 *
	 * @param position
	 */
	public TimelineDto(Position position) {
		setTitle(position.getTitle());
		setStartDate(position.getStartDate());
		setEndDate(position.getEndDate());
		setIcon(position.getIcon());

		StringBuilder positionDescription = new StringBuilder();
		positionDescription.append(position.getTitle());
		if (position.getCompany() != null) {
			positionDescription.append(" at ");
			positionDescription.append(position.getCompany().getName());
		}

		setDescription(positionDescription.toString());
		setIsEducation(false);
	}

	/**
	 * Constructor
	 *
	 * @param education
	 */
	public TimelineDto(Education education) {
		setTitle(education.getDegree());
		setStartDate(education.getStartDate());
		setEndDate(education.getEndDate());
		setIcon(education.getIcon());

		StringBuilder educationDescription = new StringBuilder();
		String fieldOfStudy = education.getFieldOfStudy();
		String schoolName = education.getSchoolName();

		if (!StringUtils.isEmpty(fieldOfStudy) && !StringUtils.isEmpty(schoolName)) {
			educationDescription.append(fieldOfStudy);
			educationDescription.append(" at ");
			educationDescription.append(schoolName);
		} else if (!StringUtils.isEmpty(fieldOfStudy)) {
			educationDescription.append(fieldOfStudy);
		} else if (!StringUtils.isEmpty(schoolName)) {
			educationDescription.append(schoolName);
		}

		setDescription(educationDescription.toString());
		setIsEducation(true);
	}

	/**
	 * Returns the title
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 *
	 * @param title
	 *            the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the start date
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date
	 *
	 * @param startDate
	 *            the start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns the end date
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date
	 *
	 * @param endDate
	 *            the end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the icon
	 *
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Sets the icon
	 *
	 * @param icon
	 *            the icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Returns the description
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 *
	 * @param description
	 *            the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the isEducation property
	 *
	 * @return the isEducation property
	 */
	public Boolean getIsEducation() {
		return isEducation;
	}

	/**
	 * Sets the isEducation property
	 *
	 * @param isEducation
	 *            the isEducation property
	 */
	public void setIsEducation(Boolean isEducation) {
		this.isEducation = isEducation;
	}

}

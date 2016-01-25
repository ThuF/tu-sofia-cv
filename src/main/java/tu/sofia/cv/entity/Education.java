package tu.sofia.cv.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class representing the T_EDUCATION database table
 */
@Entity
@Table(name = "T_EDUCATION")
public class Education implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = -177526904206998986L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long educationId;

	@Column(length = 150, nullable = false)
	private String degree;

	@Column(length = 150, nullable = false)
	private String schoolName;

	@Column(length = 150)
	private String fieldOfStudy;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date endDate;

	private String icon;

	/**
	 * Returns the education Id
	 *
	 * @return the education Id
	 */
	public Long getEducationId() {
		return educationId;
	}

	/**
	 * Sets the education Id
	 *
	 * @param educationId
	 *            the education Id
	 */
	public void setEducationId(Long educationId) {
		this.educationId = educationId;
	}

	/**
	 * Returns the degree
	 *
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * Sets the degree
	 *
	 * @param degree
	 *            the degree
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * Returns the school name
	 *
	 * @return the school name
	 */
	public String getSchoolName() {
		return schoolName;
	}

	/**
	 * Sets the school name
	 *
	 * @param schoolName
	 *            the school name
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * Returns the field of study
	 *
	 * @return the field of study
	 */
	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	/**
	 * Sets the field of study
	 *
	 * @param fieldOfStudy
	 *            the field of study
	 */
	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
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
	 * Returns the endDate
	 *
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the endDate
	 *
	 * @param endDate
	 *            the endDate
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

	@Override
	public Long getKeyValue() {
		return getEducationId();
	}

}

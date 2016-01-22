package tu.sofia.cv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class representing the T_PERSONAL_INFO database table
 */
@Entity
@Table(name = "T_PERSONAL_INFO")
public class PersonalInfo implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = 2513369403512173499L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long personalInfoId;

	@Column(length = 50, nullable = false)
	private String firstName;

	@Column(length = 50, nullable = false)
	private String lastName;

	@Column(length = 150, nullable = false)
	private String headline;

	/**
	 * Returns the personal info Id
	 *
	 * @return the personal info Id
	 */
	public Long getPersonalInfoId() {
		return personalInfoId;
	}

	/**
	 * Sets the personal info Id
	 *
	 * @param personalInfoId
	 *            the personal info Id
	 */
	public void setPersonalInfoId(Long personalInfoId) {
		this.personalInfoId = personalInfoId;
	}

	/**
	 * Returns the first name
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name
	 *
	 * @param firstName
	 *            the first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the last name
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name
	 *
	 * @param lastName
	 *            the last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the headline
	 *
	 * @return the headline
	 */
	public String getHeadline() {
		return headline;
	}

	/**
	 * Sets the headline
	 *
	 * @param headline
	 *            the headline
	 */
	public void setHeadline(String headline) {
		this.headline = headline;
	}

	@Override
	public Long getKeyValue() {
		return getPersonalInfoId();
	}

}

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

	@Column(length = 150)
	private String facebookProfileUrl;

	@Column(length = 150)
	private String twitterProfileUrl;

	@Column(length = 150)
	private String linkedinProfileUrl;

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

	/**
	 * Returns the facebook profile URL
	 *
	 * @return the facebook profile URL
	 */
	public String getFacebookProfileUrl() {
		return facebookProfileUrl;
	}

	/**
	 * Sets the facebook profile URL
	 *
	 * @param facebookProfileUrl
	 *            the facebook profile URL
	 */
	public void setFacebookProfileUrl(String facebookProfileUrl) {
		this.facebookProfileUrl = facebookProfileUrl;
	}

	/**
	 * Returns the twitter profile URL
	 *
	 * @return the twitter profile URL
	 */
	public String getTwitterProfileUrl() {
		return twitterProfileUrl;
	}

	/**
	 * Sets the twitter profile URL
	 * 
	 * @param twitterProfileUrl
	 *            the twitter profile URL
	 */
	public void setTwitterProfileUrl(String twitterProfileUrl) {
		this.twitterProfileUrl = twitterProfileUrl;
	}

	/**
	 * Returns the linkedin profile URL
	 * 
	 * @return the linkedin profile URL
	 */
	public String getLinkedinProfileUrl() {
		return linkedinProfileUrl;
	}

	/**
	 * Sets the linkedin profile URL
	 * 
	 * @param linkedinProfileUrl
	 */
	public void setLinkedinProfileUrl(String linkedinProfileUrl) {
		this.linkedinProfileUrl = linkedinProfileUrl;
	}

	@Override
	public Long getKeyValue() {
		return getPersonalInfoId();
	}

}

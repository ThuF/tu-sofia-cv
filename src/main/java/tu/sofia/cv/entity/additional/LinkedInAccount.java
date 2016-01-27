package tu.sofia.cv.entity.additional;

import com.google.gson.annotations.SerializedName;

/**
 * LinkedIn account entity
 */
public class LinkedInAccount {

	private String firstName;

	private String lastName;

	private String headline;

	@SerializedName("siteStandardProfileRequest")
	private ProfileRequest profileRequest;

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
	 * Returns the profile request
	 * 
	 * @return the profile request
	 */
	public ProfileRequest getProfileRequest() {
		return profileRequest;
	}

	/**
	 * Sets the profile request
	 * 
	 * @param profileRequest
	 *            the profile request
	 */
	public void setProfileRequest(ProfileRequest profileRequest) {
		this.profileRequest = profileRequest;
	}

	/**
	 * Profile request entity
	 */
	public static class ProfileRequest {
		private String url;

		/**
		 * Returns the URL
		 * 
		 * @return the URL
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * Sets the URL
		 * 
		 * @param url
		 *            the URL
		 */
		public void setUrl(String url) {
			this.url = url;
		}
	}
}

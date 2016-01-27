package tu.sofia.cv.entity.additional;

import com.google.gson.annotations.SerializedName;

/**
 * GitHub account entity
 */
public class GitHubAccount {

	@SerializedName("login")
	private String user;

	@SerializedName("html_url")
	private String profileUrl;

	@SerializedName("avatar_url")
	private String avatarUrl;

	/**
	 * Returns the user
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user
	 *
	 * @param user
	 *            the user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Returns the profile URL
	 * 
	 * @return the profile URL
	 */
	public String getProfileUrl() {
		return profileUrl;
	}

	/**
	 * Sets the profile URL
	 * 
	 * @param profileUrl
	 *            the profile URL
	 */
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	/**
	 * Returns the avatar URL
	 * 
	 * @return the avatar URL
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * Sets the avatar URL
	 * 
	 * @param avatarUrl
	 *            the avatar URL
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
}

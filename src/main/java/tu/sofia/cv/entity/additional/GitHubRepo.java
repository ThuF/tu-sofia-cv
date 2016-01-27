package tu.sofia.cv.entity.additional;

import com.google.gson.annotations.SerializedName;

/**
 * GitHub repo entity
 */
public class GitHubRepo {

	private String name;

	@SerializedName("html_url")
	private String url;

	private GitHubAccount owner;

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

	/**
	 * Returns the owner
	 * 
	 * @return the owner
	 */
	public GitHubAccount getOwner() {
		return owner;
	}

	/**
	 * Sets the owner
	 * 
	 * @param owner
	 *            the owner
	 */
	public void setOwner(GitHubAccount owner) {
		this.owner = owner;
	}
}

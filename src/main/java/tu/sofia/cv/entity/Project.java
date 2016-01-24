package tu.sofia.cv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class representing the T_PROJECT database table
 */
@Entity
@Table(name = "T_PROJECT")
public class Project implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = -8781817666319382716L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;

	@Column(length = 150, nullable = false)
	private String name;

	@Column(length = 150)
	private String category;

	@Column(length = 512)
	private String icon;

	@Column(length = 512)
	private String previewImage;

	@Column(length = 512)
	private String url;

	@Column(length = 1024)
	private String description;

	private Boolean shouldDisplay = Boolean.TRUE;

	/**
	 * Returns the project Id
	 *
	 * @return the project Id
	 */
	public Long getProjectId() {
		return projectId;
	}

	/**
	 * Sets the project Id
	 *
	 * @param projectId
	 *            the project Id
	 */
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	/**
	 * Returns the category
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category
	 * 
	 * @param category
	 *            the category
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * Returns the preview image
	 *
	 * @return the preview image
	 */
	public String getPreviewImage() {
		return previewImage;
	}

	/**
	 * Sets the preview image
	 *
	 * @param previewImage
	 *            the preview image
	 */
	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
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
	 * Returns the should display property
	 *
	 * @return the should display property
	 */
	public Boolean getShouldDisplay() {
		return shouldDisplay;
	}

	/**
	 * Sets the should display property
	 *
	 * @param shouldDisplay
	 *            the should display property
	 */
	public void setShouldDisplay(Boolean shouldDisplay) {
		this.shouldDisplay = shouldDisplay;
	}

	@Override
	public Long getKeyValue() {
		return getProjectId();
	}

}

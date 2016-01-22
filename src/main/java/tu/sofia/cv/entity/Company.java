package tu.sofia.cv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class representing the T_COMPANY database table
 */
@Entity
@Table(name = "T_COMPANY")
public class Company implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = -7833066729367797987L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long companyId;

	@Column(length = 150, nullable = false)
	private String name;

	private Integer size;

	/**
	 * Returns the company Id
	 *
	 * @return the company Id
	 */
	public Long getCompanyId() {
		return companyId;
	}

	/**
	 * Sets the company Id
	 *
	 * @param companyId
	 *            the company Id
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
	 * Returns the size
	 *
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * Sets the size
	 *
	 * @param size
	 *            the size
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public Long getKeyValue() {
		return getCompanyId();
	}

}

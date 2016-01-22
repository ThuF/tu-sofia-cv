package tu.sofia.cv.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class representing the T_POSITION database table
 */
@Entity
@Table(name = "T_POSITION")
@NamedQueries(@NamedQuery(name = Position.QUERY_NAME_FIND_BY_NULL_END_DATE, query = Position.QUERY_FIND_BY_NULL_END_DATE))
public class Position implements IJPAEntity<Long>, Serializable {

	static final String QUERY_FIND_BY_NULL_END_DATE = "select p from Position p where p.endDate is null";

	/**
	 * The name of the query for finding all positions with end date null
	 */
	public static final String QUERY_NAME_FIND_BY_NULL_END_DATE = "findByNullEndDate";

	private static final long serialVersionUID = -5154928578394942815L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long positionId;

	@Column(length = 150, nullable = false)
	private String title;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToOne
	private Company company;

	/**
	 * Returns the position Id
	 *
	 * @return the position Id
	 */
	public Long getPositionId() {
		return positionId;
	}

	/**
	 * Sets the position Id
	 *
	 * @param positionId
	 *            the position Id
	 */
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
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
	 * Returns the company
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company
	 *
	 * @param company
	 *            the company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public Long getKeyValue() {
		return getPositionId();
	}

}

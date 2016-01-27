package tu.sofia.cv.entity.additional;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * LinkedIn positions entity
 */
public class LinkedInPositions {

	@SerializedName("positions")
	private LinkedInPositionsWrapper values;

	/**
	 * Returns the values
	 *
	 * @return the values
	 */
	public LinkedInPositionsWrapper getValues() {
		return values;
	}

	/**
	 * Sets the values
	 *
	 * @param values
	 *            the values
	 */
	public void setValues(LinkedInPositionsWrapper values) {
		this.values = values;
	}

	/**
	 * LinkedIn positions wrapper entity
	 */
	public static class LinkedInPositionsWrapper {

		@SerializedName("_total")
		private Integer count;

		@SerializedName("values")
		private List<LinkedInPosition> positions;

		/**
		 * Returns the count
		 *
		 * @return the count
		 */
		public Integer getCount() {
			return count;
		}

		/**
		 * Sets the count
		 *
		 * @param count
		 *            the count
		 */
		public void setCount(Integer count) {
			this.count = count;
		}

		/**
		 * Returns the positions
		 *
		 * @return the positions
		 */
		public List<LinkedInPosition> getPositions() {
			return positions;
		}

		/**
		 * Sets the positions
		 *
		 * @param positions
		 *            the positions
		 */
		public void setPositions(List<LinkedInPosition> positions) {
			this.positions = positions;
		}

		/**
		 * LinkedIn position entity
		 */
		public static class LinkedInPosition {

			private String title;
			private LinkedInDate startDate;
			private LinkedInDate endDate;
			private LinkedInCompany company;

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
			public LinkedInDate getStartDate() {
				return startDate;
			}

			/**
			 * Sets the start date
			 * 
			 * @param startDate
			 *            the start date
			 */
			public void setStartDate(LinkedInDate startDate) {
				this.startDate = startDate;
			}

			/**
			 * Returns the end date
			 * 
			 * @return the end date
			 */
			public LinkedInDate getEndDate() {
				return endDate;
			}

			/**
			 * Sets the end date
			 * 
			 * @param endDate
			 *            the end date
			 */
			public void setEndDate(LinkedInDate endDate) {
				this.endDate = endDate;
			}

			/**
			 * Returns the company
			 * 
			 * @return the company
			 */
			public LinkedInCompany getCompany() {
				return company;
			}

			/**
			 * Sets the company
			 * 
			 * @param company
			 *            the company
			 */
			public void setCompany(LinkedInCompany company) {
				this.company = company;
			}
		}

		/**
		 * LinkedIn date entity
		 */
		public static class LinkedInDate {

			private Integer month;
			private Integer year;

			/**
			 * Returns the month
			 * 
			 * @return the month
			 */
			public Integer getMonth() {
				return month;
			}

			/**
			 * Sets the month
			 * 
			 * @param month
			 */
			public void setMonth(Integer month) {
				this.month = month;
			}

			/**
			 * Returns the year
			 * 
			 * @return the year
			 */
			public Integer getYear() {
				return year;
			}

			/**
			 * Sets the year
			 * 
			 * @param year
			 *            the year
			 */
			public void setYear(Integer year) {
				this.year = year;
			}
		}

		/**
		 * LinkedIn company entity
		 */
		public static class LinkedInCompany {

			private String name;
			private String size;

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
			public String getSize() {
				return size;
			}

			/**
			 * Sets the size
			 * 
			 * @param size
			 *            the size
			 */
			public void setSize(String size) {
				this.size = size;
			}
		}
	}
}

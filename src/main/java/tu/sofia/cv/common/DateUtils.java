package tu.sofia.cv.common;

import java.util.Calendar;
import java.util.Date;

import com.google.inject.Singleton;

/**
 * Utility class for working with dates
 */
@Singleton
public class DateUtils {

	private Calendar calendar;

	/**
	 * Constructor
	 */
	public DateUtils() {
		calendar = Calendar.getInstance();
	}

	/**
	 * Returns date
	 * 
	 * @param year
	 * @param month
	 * @return date
	 */
	public Date getDate(int year, int month) {
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}
}

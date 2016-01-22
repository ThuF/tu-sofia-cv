package tu.sofia.cv.common;

import com.google.inject.Singleton;
import com.google.inject.persist.UnitOfWork;

import tu.sofia.cv.service.inject.ApplicationContextListener;

/**
 * Utility class for working with Unit Of Work
 */
@Singleton
public class UnitOfWorkUtils {

	private final UnitOfWork unitOfWork;

	/**
	 * Constructor
	 */
	public UnitOfWorkUtils() {
		unitOfWork = ApplicationContextListener.getStaticInjector().getInstance(UnitOfWork.class);
	}

	/**
	 * Starts a Unit Of Work
	 */
	public void begin() {
		try {
			unitOfWork.begin();
		} catch (IllegalStateException e) {
			// Ignore it
		}
	}

	/**
	 * Ends a Unit Of Work
	 */
	public void end() {
		unitOfWork.end();
	}
}

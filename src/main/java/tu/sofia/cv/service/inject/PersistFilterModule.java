package tu.sofia.cv.service.inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.ServletModule;

/**
 * Guice module for enabling overall startup and stop of the persistence module
 */
public class PersistFilterModule extends ServletModule {

	private static final Logger logger = LoggerFactory.getLogger(PersistFilterModule.class);
	private static final String DEBUG_CONFIGURING_MODULE_MESSAGE = "Configuring PersistFilterModule";

	@Override
	protected void configureServlets() {
		logger.debug(DEBUG_CONFIGURING_MODULE_MESSAGE);

		filter("/*").through(PersistFilter.class);
	}
}

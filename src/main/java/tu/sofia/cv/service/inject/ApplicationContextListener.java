package tu.sofia.cv.service.inject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.servlet.GuiceServletContextListener;

import tu.sofia.cv.service.CompanyService;
import tu.sofia.cv.service.EducationService;
import tu.sofia.cv.service.GeneralExceptionHandler;
import tu.sofia.cv.service.GsonMessageBodyHandler;
import tu.sofia.cv.service.PersonalInfoService;
import tu.sofia.cv.service.PositionService;
import tu.sofia.cv.service.ProjectService;
import tu.sofia.cv.service.RolesPublicService;
import tu.sofia.cv.service.SkillService;
import tu.sofia.cv.service.proxy.publics.PersonalInfoPublicProxy;
import tu.sofia.cv.service.proxy.publics.SkillPublicProxy;

/**
 * This class handles the initialization of all Guice modules and all REST API
 * resources.
 */
public class ApplicationContextListener extends GuiceServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

	private static final String DEBUG_INITIALIZING_GUICE_INJECTOR_MESSAGE = "Initializing Guice Injector with modules for dependency injection";
	private static final String DEBUG_REGISTERING_API_SERVICES_MESSAGE = "Registering API services";
	private static final String DEBUG_API_SERVICES_REGISTED_MESSAGE = "API services registed: [{}]";
	private static final String DEBUG_STARTING_PERSISTENCE_SERVICE_MESSAGE = "Starting persistence service throuh Guice injector";

	private static final Set<Object> singletons = new HashSet<Object>();
	private static Injector staticInjector;

	private Injector injector;

	@Override
	protected Injector getInjector() {
		logger.debug(DEBUG_INITIALIZING_GUICE_INJECTOR_MESSAGE);
		injector = Guice.createInjector(new DataSourceModule(), new PersistFilterModule());
		ApplicationContextListener.staticInjector = injector;
		return injector;
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		startPersistenceService();
		registerAPIServices();
	}

	private void startPersistenceService() {
		logger.debug(DEBUG_STARTING_PERSISTENCE_SERVICE_MESSAGE);
		final PersistService persistService = injector.getInstance(PersistService.class);
		persistService.start();
	}

	private void registerAPIServices() {
		logger.debug(DEBUG_REGISTERING_API_SERVICES_MESSAGE);
		addServices();
		logger.debug(DEBUG_API_SERVICES_REGISTED_MESSAGE, Arrays.asList(getSingletons().toArray()));
	}

	private void addServices() {
		getSingletons().add(new GsonMessageBodyHandler<Object>());
		getSingletons().add(new GeneralExceptionHandler());

		// Services protected with the Admin role
		getSingletons().add(injector.getInstance(PersonalInfoService.class));
		getSingletons().add(injector.getInstance(SkillService.class));
		getSingletons().add(injector.getInstance(EducationService.class));
		getSingletons().add(injector.getInstance(ProjectService.class));
		getSingletons().add(injector.getInstance(CompanyService.class));
		getSingletons().add(injector.getInstance(PositionService.class));

		// Public services
		getSingletons().add(injector.getInstance(RolesPublicService.class));

		// Public proxy services
		getSingletons().add(injector.getInstance(PersonalInfoPublicProxy.class));
		getSingletons().add(injector.getInstance(SkillPublicProxy.class));
	}

	/**
	 * Get singleton services registered to this application.
	 *
	 * @return all singleton services.
	 */
	public static Set<Object> getSingletons() {
		return singletons;
	}

	/**
	 * @return injector
	 */
	public static Injector getStaticInjector() {
		return staticInjector;
	}
}

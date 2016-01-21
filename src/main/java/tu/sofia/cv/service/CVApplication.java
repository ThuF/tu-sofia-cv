package tu.sofia.cv.service;

import java.util.Set;

import javax.ws.rs.core.Application;

import tu.sofia.cv.service.inject.ApplicationContextListener;

/**
 * This class extends the CXF Application and adds CXF services
 */
public class CVApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return ApplicationContextListener.getSingletons();
	}
}

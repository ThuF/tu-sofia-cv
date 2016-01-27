package tu.sofia.cv.common;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.inject.Singleton;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

/**
 * Utility class used for working with destinations
 */
@Singleton
public class DestinationUtils {

	private static ConnectivityConfiguration configuration = null;

	private static final String JNDI_CONNECTIVITY_CONFIGURATION = "java:comp/env/connectivityConfiguration";

	/**
	 * Constructor
	 */
	public DestinationUtils() {
	}

	/**
	 * Returns the destination
	 *
	 * @param destinationName
	 * @return the destination
	 * @throws NamingException
	 */
	public DestinationConfiguration getDestnation(String destinationName) throws NamingException {
		if (configuration == null) {
			InitialContext ctx = new InitialContext();
			configuration = (ConnectivityConfiguration) ctx.lookup(JNDI_CONNECTIVITY_CONFIGURATION);
		}
		return configuration.getConfiguration(destinationName);
	}

	/**
	 * Returns the URL property from the destination
	 *
	 * @param destinationName
	 * @return the URL property from the destination
	 * @throws NamingException
	 */
	public String getDestinationUrl(String destinationName) throws NamingException {
		String url = null;
		DestinationConfiguration destination = getDestnation(destinationName);
		if (destination != null) {
			url = destination.getProperty(ICommonConstants.Destinations.Properties.URL);
		}
		return url;
	}
}

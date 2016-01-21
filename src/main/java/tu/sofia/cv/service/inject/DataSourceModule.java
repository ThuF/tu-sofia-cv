package tu.sofia.cv.service.inject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.ProvisionException;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * Guice module for providing DataSource for dependency injection
 */
public class DataSourceModule extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceModule.class);

	private static final String DEBUG_CONFIGURING_MODULE_MESSAGE = "Configuring DataSourceModule";
	private static final String DEBUG_ACTIVATING_HANA_DRIVER_MESSAGE = "Activating HANA driver";
	private static final String DEBUG_ACTIVATING_DERBY_DRIVER_MESSAGE = "Activating Derby driver";
	private static final String DEBUG_ACTIVATING_MAX_DB_DRIVER_MESSAGE = "Activating MaxDB driver";
	private static final String DEBUG_DATABASE_MESSAGE = "Database Product Name: [{}], Product Version: [{}], JDBC Driver Name: [{}], JDBC Driver Version: [{}]";
	private static final String DEBUG_BIND_1_MESSAGE = "DataSource.class bind to instance: [{}]";

	private static final String ERROR_DATA_SOURCE_INITIALIZATION_FAILED_MESSAGE = "DataSource initialization failed";

	private static final String HDB = "HDB";
	private static final String APACHE_DERBY = "Apache Derby";
	private static final String MAX_DB = "MaxDB";
	private static final String SAP_DB = "SAP DB";

	private static final String DATA_SOURCE = "java:comp/env/jdbc/DefaultDB";
	private static final String JPA_PERSIST_MODULE = "tu.sofia.cv";

	@Override
	protected void configure() {
		logger.debug(DEBUG_CONFIGURING_MODULE_MESSAGE);

		try {
			InitialContext context = new InitialContext();
			Properties properties = initializeDataSource(context);
			install(new JpaPersistModule(JPA_PERSIST_MODULE).properties(properties));
		} catch (NamingException e) {
			logger.error(ERROR_DATA_SOURCE_INITIALIZATION_FAILED_MESSAGE, e);
			throw new ProvisionException(ERROR_DATA_SOURCE_INITIALIZATION_FAILED_MESSAGE, e);
		}
	}

	private Properties initializeDataSource(InitialContext context) throws NamingException {
		try {
			final Properties properties = new Properties();

			final DataSource dataSource = (DataSource) context.lookup(DATA_SOURCE);

			properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, dataSource);

			bind(DataSource.class).toInstance(dataSource);

			logger.debug(DEBUG_BIND_1_MESSAGE, dataSource);

			try (Connection con = dataSource.getConnection()) {
				final String productName = con.getMetaData().getDatabaseProductName();
				logger.debug(DEBUG_DATABASE_MESSAGE, productName, con.getMetaData().getDatabaseProductVersion(), con.getMetaData().getDriverName(),
						con.getMetaData().getDriverVersion());

				if (productName.equalsIgnoreCase(HDB)) {
					logger.debug(DEBUG_ACTIVATING_HANA_DRIVER_MESSAGE);
					properties.put(PersistenceUnitProperties.TARGET_DATABASE, TargetDatabase.HANA);
				} else if (productName.equalsIgnoreCase(APACHE_DERBY)) {
					logger.debug(DEBUG_ACTIVATING_DERBY_DRIVER_MESSAGE);
					properties.put(PersistenceUnitProperties.TARGET_DATABASE, TargetDatabase.Derby);
				} else if (productName.equalsIgnoreCase(MAX_DB) || productName.equalsIgnoreCase(SAP_DB)) {
					logger.debug(DEBUG_ACTIVATING_MAX_DB_DRIVER_MESSAGE);
					properties.put(PersistenceUnitProperties.TARGET_DATABASE, TargetDatabase.MaxDB);
				}
			}

			properties.put(PersistenceUnitProperties.CACHE_SHARED_DEFAULT, Boolean.FALSE.toString());
			properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);

			return properties;
		} catch (final SQLException e) {
			logger.error(ERROR_DATA_SOURCE_INITIALIZATION_FAILED_MESSAGE, e);
			throw new ProvisionException(ERROR_DATA_SOURCE_INITIALIZATION_FAILED_MESSAGE, e);
		}
	}
}

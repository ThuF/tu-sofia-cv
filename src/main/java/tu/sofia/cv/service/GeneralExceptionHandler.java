package tu.sofia.cv.service;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tu.sofia.cv.validation.ApplicationExceptionMessage;

/**
 * Handles all exceptions and wraps them in {@link ApplicationExceptionMessage}.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class GeneralExceptionHandler implements ExceptionMapper<Throwable> {

	private static final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

	private static final String ERROR_INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";

	@Override
	public Response toResponse(final Throwable e) {
		ApplicationExceptionMessage applicationException = new ApplicationExceptionMessage();
		if (e instanceof WebApplicationException) {
			applicationException.setStatus(((WebApplicationException) e).getResponse().getStatus());
			applicationException.setMessage(e.getMessage());
		} else {
			logger.error(ERROR_INTERNAL_SERVER_ERROR_MESSAGE, e);
			applicationException.setStatus(Status.INTERNAL_SERVER_ERROR);
			applicationException.setMessage(ERROR_INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return Response.status(applicationException.getStatus()).type(MediaType.APPLICATION_JSON).entity(applicationException).build();
	}
}

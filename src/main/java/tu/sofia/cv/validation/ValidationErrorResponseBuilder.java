package tu.sofia.cv.validation;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import tu.sofia.cv.validation.interfaces.IValidator;

/**
 * Builds validation error response
 */
public class ValidationErrorResponseBuilder {

	/**
	 * Returns response
	 *
	 * @param status
	 * @param errorMessage
	 * @return response
	 */
	public static Response toResponse(Status status, String errorMessage) {
		return Response.status(status).entity(new ApplicationExceptionMessage(status, errorMessage)).build();
	}

	/**
	 * Returns HTTP 400 Bad Request response
	 *
	 * @param validator
	 * @return HTTP 400 Bad Request response
	 */
	public static Response toResponse(IValidator<?> validator) {
		Status status = validator.getResponseStatus() != null ? validator.getResponseStatus() : Status.BAD_REQUEST;
		return toResponse(status, validator.getValidationMessage());
	}
}

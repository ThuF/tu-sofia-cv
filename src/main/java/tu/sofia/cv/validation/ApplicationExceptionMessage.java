package tu.sofia.cv.validation;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;

/**
 * Error message that is returned if an application error occur
 */
public class ApplicationExceptionMessage implements Serializable {

	private static final long serialVersionUID = 1395649967229346126L;

	private int status;

	private String message;

	/**
	 * Default constructor
	 */
	public ApplicationExceptionMessage() {
	}

	/**
	 * Constructor
	 *
	 * @param status
	 * @param message
	 */
	public ApplicationExceptionMessage(Status status, String message) {
		this(status.getStatusCode(), message);
	}

	/**
	 * Constructor
	 *
	 * @param status
	 * @param message
	 */
	public ApplicationExceptionMessage(int status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * Returns the status
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status
	 *
	 * @param status
	 *            the status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Sets the status
	 *
	 * @param status
	 *            the status
	 */
	public void setStatus(Status status) {
		setStatus(status.getStatusCode());
	}

	/**
	 * Returns the message
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message
	 *
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}

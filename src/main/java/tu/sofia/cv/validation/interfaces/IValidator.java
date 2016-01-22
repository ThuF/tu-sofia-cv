package tu.sofia.cv.validation.interfaces;

/**
 * Interface for performing validations
 */
public interface IValidator {

	/**
	 * Returns the specific validation message if the room was not valid or
	 * null if it was valid
	 *
	 * @return the specific validation message if the room was not valid or
	 *         null if it was valid
	 */
	public String getValidationMessage();
}

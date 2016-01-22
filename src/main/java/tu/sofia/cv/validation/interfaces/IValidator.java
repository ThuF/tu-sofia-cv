package tu.sofia.cv.validation.interfaces;

import javax.ws.rs.core.Response.Status;

import tu.sofia.cv.entity.IJPAEntity;

/**
 * Interface for performing validations
 *
 * @param <Entity>
 */
public interface IValidator<Entity extends IJPAEntity<?>> {

	/**
	 * Returns true if the entity can be used for creation of a new record
	 *
	 * @param entity
	 * @return true if the entity can be used for creation of a new record
	 */
	public boolean isValidCreate(Entity entity);

	/**
	 * Returns true if the entity can be used for update of an existing records
	 *
	 * @param entity
	 * @return true if the entity can be used for update of an existing records
	 */
	public boolean isValidUpdate(Entity entity);

	/**
	 * Returns the specific validation message if the room was not valid or
	 * null if it was valid
	 *
	 * @return the specific validation message if the room was not valid or
	 *         null if it was valid
	 */
	public String getValidationMessage();

	/**
	 * Returns the response status after validation
	 *
	 * @return the response status after validation
	 */
	public Status getResponseStatus();
}

package tu.sofia.cv.validation;

import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.dao.PersonalInfoDao;
import tu.sofia.cv.entity.PersonalInfo;
import tu.sofia.cv.validation.interfaces.IValidator;

/**
 * Class for validating {@link PersonalInfo}
 */
@Singleton
public class PersonalInfoValidator implements IValidator<PersonalInfo> {

	private static final String VALIDATION_MESSAGE_THE_PERSONAL_INFO_CAN_T_BE_NULL = "The personal info can't be null";
	private static final String VALIDATION_MESSAGE_THE_FIRST_NAME_PROPERTY_CAN_T_BE_NULL = "The [firstName] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_LAST_NAME_PROPERTY_CAN_T_BE_NULL = "The [lastName] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_HEADLINE_PROPERTY_CAN_T_BE_NULL = "The [headline] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_PERSONAL_INFO_WAS_ALREADY_CREATED = "The personal info was already created";
	private static final String VALIDATION_MESSAGE_THERE_IS_NO_PERSONAL_INFO = "There is no personal info";

	private PersonalInfoDao dao;

	private String validationMessage;
	private Status validationStatus;

	/**
	 * Constructor
	 *
	 * @param dao
	 */
	@Inject
	public PersonalInfoValidator(PersonalInfoDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean isValidCreate(PersonalInfo entity) {
		validationMessage = null;
		if (haveAllProperties(entity)) {
			if (dao.findAll().isEmpty()) {
				return true;
			}
			validationMessage = VALIDATION_MESSAGE_THE_PERSONAL_INFO_WAS_ALREADY_CREATED;
		}
		return false;
	}

	@Override
	public boolean isValidUpdate(PersonalInfo entity) {
		validationMessage = null;
		if (haveAllProperties(entity)) {
			if (!dao.findAll().isEmpty()) {
				return true;
			}
			validationMessage = VALIDATION_MESSAGE_THERE_IS_NO_PERSONAL_INFO;
			validationStatus = Status.NOT_FOUND;
		}
		return false;
	}

	private boolean haveAllProperties(PersonalInfo entity) {
		boolean isValid = false;
		if (entity == null) {
			validationMessage = VALIDATION_MESSAGE_THE_PERSONAL_INFO_CAN_T_BE_NULL;
		} else if (entity.getFirstName() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_FIRST_NAME_PROPERTY_CAN_T_BE_NULL;
		} else if (entity.getLastName() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_LAST_NAME_PROPERTY_CAN_T_BE_NULL;
		} else if (entity.getHeadline() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_HEADLINE_PROPERTY_CAN_T_BE_NULL;
		} else {
			isValid = true;
		}
		return isValid;
	}

	@Override
	public String getValidationMessage() {
		return validationMessage;
	}

	@Override
	public Status getResponseStatus() {
		return validationStatus;
	}

}

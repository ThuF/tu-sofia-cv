package tu.sofia.cv.validation;

import com.google.inject.Singleton;

import tu.sofia.cv.dao.PersonalInfoDao;
import tu.sofia.cv.entity.PersonalInfo;
import tu.sofia.cv.validation.interfaces.IPersonalInfoValidator;

/**
 * Class for validating {@link PersonalInfo}
 */
@Singleton
public class PersonalInfoValidator implements IPersonalInfoValidator {

	private static final String VALIDATION_MESSAGE_THE_PERSONAL_INFO_CAN_T_BE_NULL = "The personal info can't be null";
	private static final String VALIDATION_MESSAGE_THE_FIRST_NAME_PROPERTY_CAN_T_BE_NULL = "The [firstName] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_LAST_NAME_PROPERTY_CAN_T_BE_NULL = "The [lastName] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_HEADLINE_PROPERTY_CAN_T_BE_NULL = "The [headline] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_PERSONAL_INFO_WASL_ALREADY_CREATED = "The personal info was already created";

	private String validationMessage;

	@Override
	public String getValidationMessage() {
		return validationMessage;
	}

	@Override
	public boolean isValid(PersonalInfo personalInfo, PersonalInfoDao personalInfoDao) {
		boolean isValid = false;
		if (personalInfo == null) {
			validationMessage = VALIDATION_MESSAGE_THE_PERSONAL_INFO_CAN_T_BE_NULL;
		} else if (personalInfo.getFirstName() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_FIRST_NAME_PROPERTY_CAN_T_BE_NULL;
		} else if (personalInfo.getLastName() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_LAST_NAME_PROPERTY_CAN_T_BE_NULL;
		} else if (personalInfo.getHeadline() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_HEADLINE_PROPERTY_CAN_T_BE_NULL;
		} else if (!personalInfoDao.findAll().isEmpty()) {
			validationMessage = VALIDATION_MESSAGE_THE_PERSONAL_INFO_WASL_ALREADY_CREATED;
		} else {
			isValid = true;
		}
		return isValid;
	}

}

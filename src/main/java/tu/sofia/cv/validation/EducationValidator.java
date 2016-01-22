package tu.sofia.cv.validation;

import javax.ws.rs.core.Response.Status;

import com.google.inject.Singleton;

import tu.sofia.cv.entity.Education;
import tu.sofia.cv.validation.interfaces.IValidator;

/**
 * Class for validating {@link Education}
 */
@Singleton
public class EducationValidator implements IValidator<Education> {

	private static final String VALIDATION_MESSAGE_THE_EDUCATION_ENTITY_CAN_T_BE_NULL = "The skill entity can't be null";
	private static final String VALIDATION_MESSAGE_THE_DEGREE_PROPERTY_CAN_T_BE_NULL = "The [degree] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_SCHOOL_NAME_PROPERTY_CAN_T_BE_NULL = "The [schoolName] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_START_DATE_PROPERTY_CAN_T_BE_NULL = "The [startDate] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_END_DATE_PROPERTY_CAN_T_BE_NULL = "The [endDate] property can't be null";

	private String validationMessage;

	@Override
	public boolean isValidCreate(Education entity) {
		return haveAllProperties(entity);

	}

	@Override
	public boolean isValidUpdate(Education entity) {
		return haveAllProperties(entity);
	}

	private boolean haveAllProperties(Education entity) {
		boolean isValid = false;
		if (entity == null) {
			validationMessage = VALIDATION_MESSAGE_THE_EDUCATION_ENTITY_CAN_T_BE_NULL;
		} else if (entity.getDegree() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_DEGREE_PROPERTY_CAN_T_BE_NULL;
		} else if (entity.getSchoolName() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_SCHOOL_NAME_PROPERTY_CAN_T_BE_NULL;
		} else if (entity.getStartDate() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_START_DATE_PROPERTY_CAN_T_BE_NULL;
		} else if (entity.getEndDate() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_END_DATE_PROPERTY_CAN_T_BE_NULL;
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
		return null;
	}

}

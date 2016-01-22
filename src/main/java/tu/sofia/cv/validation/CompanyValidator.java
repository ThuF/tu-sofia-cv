package tu.sofia.cv.validation;

import javax.ws.rs.core.Response.Status;

import com.google.inject.Singleton;

import tu.sofia.cv.entity.Company;
import tu.sofia.cv.validation.interfaces.IValidator;

/**
 * Class for validating {@link Company}
 */
@Singleton
public class CompanyValidator implements IValidator<Company> {

	private static final String VALIDATION_MESSAGE_THE_COMPANY_ENTITY_CAN_T_BE_NULL = "The skill entity can't be null";
	private static final String VALIDATION_MESSAGE_THE_NAME_PROPERTY_CAN_T_BE_NULL = "The [name] property can't be null";

	private String validationMessage;

	@Override
	public boolean isValidCreate(Company entity) {
		return haveAllProperties(entity);

	}

	@Override
	public boolean isValidUpdate(Company entity) {
		return haveAllProperties(entity);
	}

	private boolean haveAllProperties(Company entity) {
		boolean isValid = false;
		if (entity == null) {
			validationMessage = VALIDATION_MESSAGE_THE_COMPANY_ENTITY_CAN_T_BE_NULL;
		} else if (entity.getName() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_NAME_PROPERTY_CAN_T_BE_NULL;
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

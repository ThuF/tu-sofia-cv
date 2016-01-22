package tu.sofia.cv.validation;

import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.dao.PositionDao;
import tu.sofia.cv.entity.Position;
import tu.sofia.cv.validation.interfaces.IValidator;

/**
 * Class for validating {@link Position}
 */
@Singleton
public class PositionValidator implements IValidator<Position> {

	private static final String VALIDATION_MESSAGE_THE_POSITION_ENTITY_CAN_T_BE_NULL = "The position entity can't be null";
	private static final String VALIDATION_MESSAGE_THE_TITLE_PROPERTY_CAN_T_BE_NULL = "The [title] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_START_DATE_PROPERTY_CAN_T_BE_NULL = "The [startDate] property can't be null";
	private static final String VALIDATION_MESSAGE_THERE_CAN_T_BE_MORE_THAT_ONE_POSITION_WITH_END_DATE_NULL = "There can't be more that one position with [endDate=null], meaning that this is the current one";

	private PositionDao dao;

	private String validationMessage;

	/**
	 * Constructor
	 *
	 * @param dao
	 */
	@Inject
	public PositionValidator(PositionDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean isValidCreate(Position entity) {
		return isValid(entity);

	}

	@Override
	public boolean isValidUpdate(Position entity) {
		return haveAllProperties(entity);
	}

	@Override
	public String getValidationMessage() {
		return validationMessage;
	}

	@Override
	public Status getResponseStatus() {
		return null;
	}

	private boolean isValid(Position entity) {
		if (haveAllProperties(entity)) {
			if (entity.getEndDate() == null) {
				if (dao.findAllByNullEndDate().isEmpty()) {
					return true;
				}
				validationMessage = VALIDATION_MESSAGE_THERE_CAN_T_BE_MORE_THAT_ONE_POSITION_WITH_END_DATE_NULL;
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean haveAllProperties(Position entity) {
		boolean isValid = false;
		if (entity == null) {
			validationMessage = VALIDATION_MESSAGE_THE_POSITION_ENTITY_CAN_T_BE_NULL;
		} else if (entity.getTitle() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_TITLE_PROPERTY_CAN_T_BE_NULL;
		} else if (entity.getStartDate() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_START_DATE_PROPERTY_CAN_T_BE_NULL;
		} else {
			isValid = true;
		}
		return isValid;
	}
}

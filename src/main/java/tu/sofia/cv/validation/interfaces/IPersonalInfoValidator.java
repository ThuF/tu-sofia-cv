package tu.sofia.cv.validation.interfaces;

import tu.sofia.cv.dao.PersonalInfoDao;
import tu.sofia.cv.entity.PersonalInfo;

/**
 * Interface for performing validations of personal info
 */
public interface IPersonalInfoValidator extends IValidator {

	/**
	 * Returns true if the personal info is valid
	 * 
	 * @param personalInfo
	 * @param personalInfoDao
	 * @return true if the personal info is valid
	 */
	boolean isValid(PersonalInfo personalInfo, PersonalInfoDao personalInfoDao);
}

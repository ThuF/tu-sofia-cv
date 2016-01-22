package tu.sofia.cv.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.common.UnitOfWorkUtils;
import tu.sofia.cv.dao.PersonalInfoDao;
import tu.sofia.cv.entity.PersonalInfo;
import tu.sofia.cv.validation.PersonalInfoValidator;
import tu.sofia.cv.validation.ValidationErrorResponseBuilder;

/**
 * Service for personal info
 */
@Singleton
@Path("/protected/admin/personal-info")
public class PersonalInfoService {

	private UnitOfWorkUtils unitOfWorkUtils;
	private PersonalInfoDao personalInfoDao;
	private PersonalInfoValidator personalInfoValidator;

	/**
	 * Constructor
	 *
	 * @param unitOfWorkUtils
	 * @param personalInfoDao
	 * @param personalInfoValidator
	 */
	@Inject
	public PersonalInfoService(UnitOfWorkUtils unitOfWorkUtils, PersonalInfoDao personalInfoDao, PersonalInfoValidator personalInfoValidator) {
		this.unitOfWorkUtils = unitOfWorkUtils;
		this.personalInfoDao = personalInfoDao;
		this.personalInfoValidator = personalInfoValidator;
	}

	/**
	 * Returns the personal info
	 *
	 * @return the personal info
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PersonalInfo getPersonalInfo() {
		unitOfWorkUtils.begin();

		List<PersonalInfo> results = personalInfoDao.findAll();
		if (results.isEmpty()) {
			throw new NotFoundException("There is no personal info");
		}

		unitOfWorkUtils.end();
		return results.get(0);
	}

	/**
	 * Adds new personal info
	 *
	 * @param personalInfo
	 * @return HTTP 201 CREATED if the personal info was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPersonalInfo(PersonalInfo personalInfo) {
		unitOfWorkUtils.begin();

		Response response = null;
		if (personalInfoValidator.isValidCreate(personalInfo)) {
			personalInfoDao.create(personalInfo);
			response = Response.status(Status.CREATED).entity(personalInfo.getPersonalInfoId()).build();
		} else {
			response = ValidationErrorResponseBuilder.toResponse(personalInfoValidator);
		}

		unitOfWorkUtils.end();
		return response;
	}

	/**
	 * Updates existing personal info
	 *
	 * @param personalInfo
	 * @return HTTP 204 NO CONTENT if the update was successful or
	 *         HTTP 404 NOT FOUND if there was no personal info
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePersonalInfo(PersonalInfo personalInfo) {
		unitOfWorkUtils.begin();

		Response response = null;
		List<PersonalInfo> results = personalInfoDao.findAll();
		if (personalInfoValidator.isValidUpdate(personalInfo)) {
			PersonalInfo persistedPersonalInfo = results.get(0);
			updatePersonalInfoProperties(persistedPersonalInfo, personalInfo);
			personalInfoDao.update(persistedPersonalInfo);
			response = Response.status(Status.NO_CONTENT).build();
		} else {
			response = ValidationErrorResponseBuilder.toResponse(personalInfoValidator);
		}

		unitOfWorkUtils.end();
		return response;
	}

	/**
	 * Deletes existing personal info
	 *
	 * @return HTTP 204 NO CONTENT if the deletion was successful or
	 *         HTTP 404 NOT FOUND if there was no personal info
	 */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePersonalInfo() {
		unitOfWorkUtils.begin();

		Response response = null;
		List<PersonalInfo> results = personalInfoDao.findAll();
		if (results.isEmpty()) {
			response = ValidationErrorResponseBuilder.toResponse(Status.NOT_FOUND, "There is no personal info");
		} else {
			personalInfoDao.delete(results.get(0));
			response = Response.status(Status.NO_CONTENT).build();
		}

		unitOfWorkUtils.end();
		return response;
	}

	private void updatePersonalInfoProperties(PersonalInfo persistedPersonalInfo, PersonalInfo personalInfo) {
		persistedPersonalInfo.setFirstName(personalInfo.getFirstName());
		persistedPersonalInfo.setLastName(personalInfo.getLastName());
		persistedPersonalInfo.setHeadline(personalInfo.getHeadline());
	}

}

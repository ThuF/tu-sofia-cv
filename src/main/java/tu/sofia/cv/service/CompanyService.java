package tu.sofia.cv.service;

import java.text.MessageFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.common.UnitOfWorkUtils;
import tu.sofia.cv.dao.CompanyDao;
import tu.sofia.cv.entity.Company;
import tu.sofia.cv.validation.CompanyValidator;

/**
 * Service for companies
 */
@Singleton
@Path("/protected/admin/companies")
public class CompanyService extends AbstractCRUDService<Long, Company> {

	private static final String ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE = "There is no company with [companyId={0}]";

	/**
	 * Constructor
	 *
	 * @param companyDao
	 * @param companyValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public CompanyService(CompanyDao companyDao, CompanyValidator companyValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(companyDao, companyValidator, unitOfWorkUtils);
	}

	/**
	 * Returns a list of all companies
	 *
	 * @return a list of all companies
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getCompanies() {
		return getAll();
	}

	/**
	 * Returns a company specified by the id path parameter
	 *
	 * @param id
	 * @return a company specified by the id path parameter
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new company
	 *
	 * @param company
	 * @return HTTP 201 CREATED if the company was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCompany(Company company) {
		return add(company);
	}

	/**
	 * Updates existing company
	 *
	 * @param id
	 * @param company
	 * @return HTTP 204 NO CONTENT if the update was successful or
	 *         HTTP 404 NOT FOUND if there was no such company
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCompany(@PathParam("id") final Long id, Company company) {
		return update(id, company);
	}

	/**
	 * Deletes existing company
	 *
	 * @param id
	 * @return HTTP 204 NO CONTENT if the deletion was successful or
	 *         HTTP 404 NOT FOUND if there was no such company
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteSkill(@PathParam("id") final Long id) {
		return delete(id);
	}

	@Override
	protected String getNotFoundMessage(Long id) {
		return MessageFormat.format(ERROR_THERE_IS_NO_SKILL_WITH_SKILL_ID_MESSAGE, id);
	}

	@Override
	protected void updateEntityProperties(Company persistedEntity, Company entity) {
		persistedEntity.setName(entity.getName());
		persistedEntity.setSize(entity.getSize());
	}

}

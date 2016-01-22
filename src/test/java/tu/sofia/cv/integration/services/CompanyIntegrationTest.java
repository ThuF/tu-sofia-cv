package tu.sofia.cv.integration.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import retrofit.RetrofitError;
import retrofit.client.Response;
import tu.sofia.cv.api.CompanyAPI;
import tu.sofia.cv.entity.Company;
import tu.sofia.cv.integration.IntegrationTestSupport;
import tu.sofia.cv.integration.UserRole;

@SuppressWarnings("javadoc")
public class CompanyIntegrationTest extends IntegrationTestSupport {

	private CompanyAPI API;
	private Company expectedCompany;
	private Company updatedCompany;

	@Before
	public void setUp() throws Exception {
		expectedCompany = createCompnay("SAP");
		updatedCompany = createCompnay("IBM");

		API = createRestAdapter().create(CompanyAPI.class);
		login(UserRole.ADMIN);
	}

	private Company createCompnay(String name) {
		Company company = new Company();
		company.setName(name);
		return company;
	}

	@After
	public void cleanUp() throws Exception {
		logout();
		login(UserRole.ADMIN);

		removeCompanies();

		logout();
	}

	private void removeCompanies() {
		for (Company next : API.get()) {
			Response response = API.delete(next.getCompanyId());
			assertResponseStatus(Status.NO_CONTENT, response);
		}
		assertTrue(API.get().isEmpty());
	}

	@Test
	public void testEmptyCompanies() throws Exception {
		List<Company> results = API.get();
		assertNotNull(results);
		assertTrue(results.isEmpty());
	}

	@Test
	public void testAddCompany() throws Exception {
		Response response = API.add(expectedCompany);
		assertResponseStatus(Status.CREATED, response);
		expectedCompany.setCompanyId(getResponseAsLong(response));

		List<Company> results = API.get();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertCompanyEquals(expectedCompany, results.get(0));
	}

	@Test
	public void testAddTwoCompanies() throws Exception {
		Response response = API.add(expectedCompany);
		assertResponseStatus(Status.CREATED, response);
		expectedCompany.setCompanyId(getResponseAsLong(response));

		response = API.add(updatedCompany);
		assertResponseStatus(Status.CREATED, response);
		updatedCompany.setCompanyId(getResponseAsLong(response));

		List<Company> results = API.get();
		assertNotNull(results);
		assertEquals(2, results.size());
		assertCompanyEquals(expectedCompany, results.get(0));
		assertCompanyEquals(updatedCompany, results.get(1));
	}

	@Test
	public void testAddCompanyMissingName() throws Exception {
		expectedCompany.setName(null);

		try {
			API.add(expectedCompany);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [name] property can't be null");
		}
	}

	@Test
	public void testUpdateCompany() throws Exception {
		Response response = API.add(expectedCompany);
		assertResponseStatus(Status.CREATED, response);
		expectedCompany.setCompanyId(getResponseAsLong(response));
		updatedCompany.setCompanyId(expectedCompany.getCompanyId());

		response = API.update(expectedCompany.getCompanyId(), updatedCompany);
		assertResponseStatus(Status.NO_CONTENT, response);

		assertCompanyEquals(updatedCompany, API.get(updatedCompany.getCompanyId()));
	}

	@Test
	public void testUpdateCompanyWhenNoCompanyIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.update(id, updatedCompany);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no company with [companyId=" + id + "]");
		}
	}

	@Test
	public void testDeleteCompany() throws Exception {
		Response response = API.add(expectedCompany);
		assertResponseStatus(Status.CREATED, response);
		expectedCompany.setCompanyId(getResponseAsLong(response));

		response = API.delete(expectedCompany.getCompanyId());
		assertResponseStatus(Status.NO_CONTENT, response);

		testEmptyCompanies();
	}

	@Test
	public void testRemoveCompanyWhenNoCompanyIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.delete(id);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no company with [companyId=" + id + "]");
		}
	}

	@Test
	public void testForbidden() throws Exception {
		logout();
		login(UserRole.EVERYONE);

		try {
			API.add(expectedCompany);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.FORBIDDEN, e.getResponse());
		}
	}

	private void assertCompanyEquals(Company expected, Company actual) {
		assertEquals("The [companyId] properties are not equals", expected.getCompanyId(), actual.getCompanyId());
		assertEquals("The [name] properties are not equals", expected.getName(), actual.getName());
		assertEquals("The [size] properties are not equals", expected.getSize(), actual.getSize());
	}
}

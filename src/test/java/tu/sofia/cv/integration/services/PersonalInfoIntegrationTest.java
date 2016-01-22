package tu.sofia.cv.integration.services;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import retrofit.RetrofitError;
import retrofit.client.Response;
import tu.sofia.cv.api.PersonalInfoAPI;
import tu.sofia.cv.entity.PersonalInfo;
import tu.sofia.cv.integration.IntegrationTestSupport;
import tu.sofia.cv.integration.UserRole;

@SuppressWarnings("javadoc")
public class PersonalInfoIntegrationTest extends IntegrationTestSupport {

	private PersonalInfoAPI API;
	private PersonalInfo expectedPersonalInfo = new PersonalInfo();
	private PersonalInfo updatedPersonalInfo = new PersonalInfo();

	@Before
	public void setUp() throws Exception {
		expectedPersonalInfo = createPersonalInfo("Yordan", "Pavlov", "Yordan Pavlov working at SAP");
		updatedPersonalInfo = createPersonalInfo("Georgi", "Georgiev", "Georgi Georgiev working at IBM");

		API = createRestAdapter().create(PersonalInfoAPI.class);
		login(UserRole.ADMIN);
	}

	private PersonalInfo createPersonalInfo(String firstName, String lastName, String headline) {
		PersonalInfo personalInfo = new PersonalInfo();
		personalInfo.setFirstName(firstName);
		personalInfo.setLastName(lastName);
		personalInfo.setHeadline(headline);
		return personalInfo;
	}

	@After
	public void cleanUp() throws Exception {
		logout();
		login(UserRole.ADMIN);

		removePersonalInfo();

		logout();
	}

	private void removePersonalInfo() {
		try {
			API.delete();
		} catch (RetrofitError e) {
			if (e.getResponse().getStatus() != Status.NOT_FOUND.getStatusCode()) {
				throw e;
			}
		}
	}

	@Test
	public void testEmptyPersonalInfo() throws Exception {
		try {
			API.get();
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no personal info");
		}
	}

	@Test
	public void testAddPersonalInfo() throws Exception {
		Response response = API.add(expectedPersonalInfo);
		assertResponseStatus(Status.CREATED, response);
		expectedPersonalInfo.setPersonalInfoId(getResponseAsLong(response));

		PersonalInfo actualPersonalInfo = API.get();
		assertPersonalInfoEquals(expectedPersonalInfo, actualPersonalInfo);
	}

	@Test
	public void testAddPersonalInfoWithMissingFirstName() throws Exception {
		expectedPersonalInfo.setFirstName(null);

		try {
			API.add(expectedPersonalInfo);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [firstName] property can't be null");
		}
	}

	@Test
	public void testAddPersonalInfoWithMissingLastName() throws Exception {
		expectedPersonalInfo.setLastName(null);

		try {
			API.add(expectedPersonalInfo);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [lastName] property can't be null");
		}
	}

	@Test
	public void testAddPersonalInfoWithMissingHeadline() throws Exception {
		expectedPersonalInfo.setHeadline(null);

		try {
			API.add(expectedPersonalInfo);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [headline] property can't be null");
		}
	}

	@Test
	public void testAddPersonalInfoTwice() throws Exception {
		Response response = API.add(expectedPersonalInfo);
		assertResponseStatus(Status.CREATED, response);

		try {
			API.add(expectedPersonalInfo);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The personal info was already created");
		}
	}

	@Test
	public void testUpdatePersonalInfo() throws Exception {
		Response response = API.add(expectedPersonalInfo);
		assertResponseStatus(Status.CREATED, response);
		updatedPersonalInfo.setPersonalInfoId(getResponseAsLong(response));

		response = API.update(updatedPersonalInfo);
		assertResponseStatus(Status.NO_CONTENT, response);

		assertPersonalInfoEquals(updatedPersonalInfo, API.get());
	}

	@Test
	public void testUpdatePersonalInfoWhenNoPersonalInfoAdded() throws Exception {
		try {
			API.update(updatedPersonalInfo);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no personal info");
		}
	}

	@Test
	public void testDeletePersonalInfo() throws Exception {
		Response response = API.add(expectedPersonalInfo);
		assertResponseStatus(Status.CREATED, response);

		response = API.delete();
		assertResponseStatus(Status.NO_CONTENT, response);

		testEmptyPersonalInfo();
	}

	@Test
	public void testRemovePersonalInfoWhenNoPersonalInfoAdded() throws Exception {
		try {
			API.delete();
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no personal info");
		}
	}

	@Test
	public void testForbidden() throws Exception {
		logout();
		login(UserRole.EVERYONE);

		try {
			API.add(expectedPersonalInfo);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.FORBIDDEN, e.getResponse());
		}
	}

	private void assertPersonalInfoEquals(PersonalInfo expected, PersonalInfo actual) {
		assertEquals("The [personalInfoId] properties are not equals", expected.getPersonalInfoId(), actual.getPersonalInfoId());
		assertEquals("The [firstName] properties are not equals", expected.getFirstName(), actual.getFirstName());
		assertEquals("The [lastName] properties are not equals", expected.getLastName(), actual.getLastName());
		assertEquals("The [headline] properties are not equals", expected.getHeadline(), actual.getHeadline());
	}
}

package tu.sofia.cv.integration.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import retrofit.RetrofitError;
import retrofit.client.Response;
import tu.sofia.cv.api.EducationAPI;
import tu.sofia.cv.entity.Education;
import tu.sofia.cv.integration.IntegrationTestSupport;
import tu.sofia.cv.integration.UserRole;

@SuppressWarnings("javadoc")
public class EducationIntegrationTest extends IntegrationTestSupport {

	private EducationAPI API;
	private Education expectedEducation;
	private Education updatedEducation;

	@Before
	public void setUp() throws Exception {
		expectedEducation = createEducation("Bachelor degree", "TU-Sofia", new Date(), new Date());
		updatedEducation = createEducation("Masters degree", "TU-Sofia", new Date(), new Date());

		API = createRestAdapter().create(EducationAPI.class);
		login(UserRole.ADMIN);
	}

	private Education createEducation(String degree, String schoolName, Date startDate, Date endDate) {
		Education education = new Education();
		education.setDegree(degree);
		education.setSchoolName(schoolName);
		education.setStartDate(startDate);
		education.setEndDate(endDate);
		return education;
	}

	@After
	public void cleanUp() throws Exception {
		logout();
		login(UserRole.ADMIN);

		removeEducations();

		logout();
	}

	private void removeEducations() {
		for (Education next : API.get()) {
			Response response = API.delete(next.getEducationId());
			assertResponseStatus(Status.NO_CONTENT, response);
		}
		assertTrue(API.get().isEmpty());
	}

	@Test
	public void testEmptyEducations() throws Exception {
		List<Education> results = API.get();
		assertNotNull(results);
		assertTrue(results.isEmpty());
	}

	@Test
	public void testAddEducation() throws Exception {
		Response response = API.add(expectedEducation);
		assertResponseStatus(Status.CREATED, response);
		expectedEducation.setEducationId(getResponseAsLong(response));

		List<Education> results = API.get();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertEducationEquals(expectedEducation, results.get(0));
	}

	@Test
	public void testAddTwoEducations() throws Exception {
		Response response = API.add(expectedEducation);
		assertResponseStatus(Status.CREATED, response);
		expectedEducation.setEducationId(getResponseAsLong(response));

		response = API.add(updatedEducation);
		assertResponseStatus(Status.CREATED, response);
		updatedEducation.setEducationId(getResponseAsLong(response));

		List<Education> results = API.get();
		assertNotNull(results);
		assertEquals(2, results.size());
		assertEducationEquals(expectedEducation, results.get(0));
		assertEducationEquals(updatedEducation, results.get(1));
	}

	@Test
	public void testAddEducationMissingDegree() throws Exception {
		expectedEducation.setDegree(null);

		try {
			API.add(expectedEducation);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [degree] property can't be null");
		}
	}

	@Test
	public void testAddEducationMissingSchoolName() throws Exception {
		expectedEducation.setSchoolName(null);

		try {
			API.add(expectedEducation);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [schoolName] property can't be null");
		}
	}

	@Test
	public void testAddEducationMissingStartDate() throws Exception {
		expectedEducation.setStartDate(null);

		try {
			API.add(expectedEducation);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [startDate] property can't be null");
		}
	}

	@Test
	public void testAdEducationMissingEndDate() throws Exception {
		expectedEducation.setEndDate(null);

		try {
			API.add(expectedEducation);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [endDate] property can't be null");
		}
	}

	@Test
	public void testUpdateEducation() throws Exception {
		Response response = API.add(expectedEducation);
		assertResponseStatus(Status.CREATED, response);
		expectedEducation.setEducationId(getResponseAsLong(response));
		updatedEducation.setEducationId(expectedEducation.getEducationId());

		response = API.update(expectedEducation.getEducationId(), updatedEducation);
		assertResponseStatus(Status.NO_CONTENT, response);

		assertEducationEquals(updatedEducation, API.get(updatedEducation.getEducationId()));
	}

	@Test
	public void testUpdateEducationWhenNoEducationIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.update(id, updatedEducation);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no education with [educationId=" + id + "]");
		}
	}

	@Test
	public void testDeleteEducation() throws Exception {
		Response response = API.add(expectedEducation);
		assertResponseStatus(Status.CREATED, response);
		expectedEducation.setEducationId(getResponseAsLong(response));

		response = API.delete(expectedEducation.getEducationId());
		assertResponseStatus(Status.NO_CONTENT, response);

		testEmptyEducations();
	}

	@Test
	public void testRemoveEducationWhenNoEducationIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.delete(id);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no education with [educationId=" + id + "]");
		}
	}

	@Test
	public void testForbidden() throws Exception {
		logout();
		login(UserRole.EVERYONE);

		try {
			API.add(expectedEducation);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.FORBIDDEN, e.getResponse());
		}
	}

	private void assertEducationEquals(Education expected, Education actual) {
		assertEquals("The [educationId] properties are not equals", expected.getEducationId(), actual.getEducationId());
		assertEquals("The [degree] properties are not equals", expected.getDegree(), actual.getDegree());
		assertEquals("The [schoolName] properties are not equals", expected.getSchoolName(), actual.getSchoolName());
		assertEquals("The [fieldOfStudy] properties are not equals", expected.getFieldOfStudy(), actual.getFieldOfStudy());
		assertEquals("The [startDate] properties are not equals", getDateOnly(expected.getStartDate()), actual.getStartDate());
		assertEquals("The [endDate] properties are not equals", getDateOnly(expected.getEndDate()), actual.getEndDate());
	}
}

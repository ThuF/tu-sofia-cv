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
import tu.sofia.cv.api.PositionAPI;
import tu.sofia.cv.entity.Position;
import tu.sofia.cv.integration.IntegrationTestSupport;
import tu.sofia.cv.integration.UserRole;

@SuppressWarnings("javadoc")
public class PositionIntegrationTest extends IntegrationTestSupport {

	private PositionAPI API;
	private Position expectedPosition;
	private Position updatedPosition;

	@Before
	public void setUp() throws Exception {
		expectedPosition = createPosition("Software Developer", new Date());
		updatedPosition = createPosition("Software Architect", new Date());

		API = createRestAdapter().create(PositionAPI.class);
		login(UserRole.ADMIN);
	}

	private Position createPosition(String title, Date startDate) {
		Position position = new Position();
		position.setTitle(title);
		position.setStartDate(startDate);
		return position;
	}

	@After
	public void cleanUp() throws Exception {
		logout();
		login(UserRole.ADMIN);

		removePositions();

		logout();
	}

	private void removePositions() {
		for (Position next : API.get()) {
			Response response = API.delete(next.getPositionId());
			assertResponseStatus(Status.NO_CONTENT, response);
		}
		assertTrue(API.get().isEmpty());
	}

	@Test
	public void testEmptyPositions() throws Exception {
		List<Position> results = API.get();
		assertNotNull(results);
		assertTrue(results.isEmpty());
	}

	@Test
	public void testAddPosition() throws Exception {
		Response response = API.add(expectedPosition);
		assertResponseStatus(Status.CREATED, response);
		expectedPosition.setPositionId(getResponseAsLong(response));

		List<Position> results = API.get();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertPositionEquals(expectedPosition, results.get(0));
	}

	@Test
	public void testAddTwoPositions() throws Exception {
		Response response = API.add(expectedPosition);
		assertResponseStatus(Status.CREATED, response);
		expectedPosition.setPositionId(getResponseAsLong(response));

		updatedPosition.setEndDate(new Date());
		response = API.add(updatedPosition);
		assertResponseStatus(Status.CREATED, response);
		updatedPosition.setPositionId(getResponseAsLong(response));

		List<Position> results = API.get();
		assertNotNull(results);
		assertEquals(2, results.size());
		assertPositionEquals(expectedPosition, results.get(0));
		assertPositionEquals(updatedPosition, results.get(1));
	}

	@Test
	public void testAddTwoPositionsWithNullEndDates() throws Exception {
		Response response = API.add(expectedPosition);
		assertResponseStatus(Status.CREATED, response);
		expectedPosition.setPositionId(getResponseAsLong(response));

		try {
			API.add(updatedPosition);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST,
					"There can't be more that one position with [endDate=null], meaning that this is the current one");
		}
	}

	@Test
	public void testAddPositionMissingTitiel() throws Exception {
		expectedPosition.setTitle(null);

		try {
			API.add(expectedPosition);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [title] property can't be null");
		}
	}

	@Test
	public void testAddPositionMissingStartDate() throws Exception {
		expectedPosition.setStartDate(null);

		try {
			API.add(expectedPosition);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [startDate] property can't be null");
		}
	}

	@Test
	public void testUpdatePosition() throws Exception {
		Response response = API.add(expectedPosition);
		assertResponseStatus(Status.CREATED, response);
		expectedPosition.setPositionId(getResponseAsLong(response));
		updatedPosition.setPositionId(expectedPosition.getPositionId());

		response = API.update(expectedPosition.getPositionId(), updatedPosition);
		assertResponseStatus(Status.NO_CONTENT, response);

		assertPositionEquals(updatedPosition, API.get(updatedPosition.getPositionId()));
	}

	@Test
	public void testUpdatePositionWhenNoPositionIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.update(id, updatedPosition);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no position with [positionId=" + id + "]");
		}
	}

	@Test
	public void testDeletePosition() throws Exception {
		Response response = API.add(expectedPosition);
		assertResponseStatus(Status.CREATED, response);
		expectedPosition.setPositionId(getResponseAsLong(response));

		response = API.delete(expectedPosition.getPositionId());
		assertResponseStatus(Status.NO_CONTENT, response);

		testEmptyPositions();
	}

	@Test
	public void testRemovePositionWhenNoPositionIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.delete(id);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no position with [positionId=" + id + "]");
		}
	}

	@Test
	public void testForbidden() throws Exception {
		logout();
		login(UserRole.EVERYONE);

		try {
			API.add(expectedPosition);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.FORBIDDEN, e.getResponse());
		}
	}

	private void assertPositionEquals(Position expected, Position actual) {
		assertEquals("The [positionId] properties are not equals", expected.getPositionId(), actual.getPositionId());
		assertEquals("The [title] properties are not equals", expected.getTitle(), actual.getTitle());
		assertEquals("The [startDate] properties are not equals", getDateOnly(expected.getStartDate()), actual.getStartDate());
		assertEquals("The [endDate] properties are not equals", getDateOnly(expected.getEndDate()), actual.getEndDate());
		assertEquals("The [company] properties are not equals", expected.getCompany(), actual.getCompany());
	}
}

package tu.sofia.cv.integration.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import retrofit.RetrofitError;
import retrofit.client.Response;
import tu.sofia.cv.api.PaymentAPI;
import tu.sofia.cv.api.RoomsAPI;
import tu.sofia.cv.entity.Room;
import tu.sofia.cv.entity.RoomPrice;
import tu.sofia.cv.entity.additional.BedType;
import tu.sofia.cv.entity.additional.RoomType;
import tu.sofia.cv.entity.additional.RoomView;
import tu.sofia.cv.integration.IntegrationTestSupport;
import tu.sofia.cv.integration.UserRole;
import tu.sofia.cv.validation.ApplicationExceptionMessage;

@SuppressWarnings("javadoc")
public class PaymentIntegrationTest extends IntegrationTestSupport {

	private static final List<RoomPrice> testData = new ArrayList<RoomPrice>();
	private PaymentAPI API;
	private RoomsAPI API_ROOMS;

	@BeforeClass
	public static void initialize() throws Exception {
		RoomPrice data1 = new RoomPrice();
		data1.setPrice(100D);
		testData.add(data1);

		RoomPrice data2 = new RoomPrice();
		data2.setPrice(250D);
		testData.add(data2);
	}

	@Before
	public void setUp() throws Exception {
		API = createRestAdapter().create(PaymentAPI.class);
		API_ROOMS = createRestAdapter().create(RoomsAPI.class);
		login(UserRole.ADMIN);

		addRooms();
	}

	private void addRooms() throws IOException {
		Room data1 = new Room();
		data1.setRoomType(RoomType.DELUXE);
		data1.setRoomView(RoomView.OCEAN_VIEW);
		data1.setBedType(BedType.CALIFORNIA_KING);
		data1.setDescription("The perfect room for a vacation!");
		Response response = API_ROOMS.add(data1);
		testData.get(0).setRoomId(getResponseAsLong(response));

		Room data2 = new Room();
		data2.setRoomType(RoomType.STANDARD);
		data2.setRoomView(RoomView.CITY_VIEW);
		data2.setBedType(BedType.DOUBLE);
		response = API_ROOMS.add(data1);
		testData.get(1).setRoomId(getResponseAsLong(response));
	}

	@After
	public void cleanUp() throws Exception {
		logout();
		login(UserRole.ADMIN);

		removeRoomPrices();
		removeRooms();

		logout();
	}

	private void removeRoomPrices() {
		for (RoomPrice next : API.get()) {
			Response response = API.remove(next.getRoomId());
			assertResponseStatus(Status.NO_CONTENT, response);
		}
		assertEquals(new Long(0), API.count());
	}

	private void removeRooms() {
		for (Room next : API_ROOMS.get()) {
			Response response = API_ROOMS.remove(next.getRoomId());
			assertResponseStatus(Status.NO_CONTENT, response);
		}
		assertEquals(new Long(0), API_ROOMS.count());
	}

	@Test
	public void testEmpty() throws Exception {
		List<RoomPrice> results = API.get();
		assertEquals(0, results.size());
		assertEquals(new Long(0), API.count());
	}

	@Test
	public void testAddEntity() throws Exception {
		Response response = API.add(testData.get(0));
		assertResponseStatus(Status.CREATED, response);
		assertEquals(new Long(1), API.count());
	}

	@Test
	public void testAddInvalidEntityShouldReturnBadRequest() throws Exception {
		try {
			API.add(new RoomPrice());
		} catch (RetrofitError e) {
			assertResponseStatus(Status.BAD_REQUEST, e.getResponse());
			ApplicationExceptionMessage message = getApplicationExceptionMessage(e);
			assertEquals(Status.BAD_REQUEST.getStatusCode(), message.getStatus());
			assertEquals("The [roomId] property can't be null", message.getMessage());
		}
	}

	@Test
	public void testAddInvalidEntityShouldReturnBadRequest2() throws Exception {
		try {
			RoomPrice roomPrice = new RoomPrice();
			roomPrice.setRoomId(testData.get(0).getRoomId());
			API.add(roomPrice);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.BAD_REQUEST, e.getResponse());
			ApplicationExceptionMessage message = getApplicationExceptionMessage(e);
			assertEquals(Status.BAD_REQUEST.getStatusCode(), message.getStatus());
			assertEquals("The [price] property can't be null", message.getMessage());
		}
	}

	@Test
	public void testAddInvalidEntityShouldReturnBadRequest3() throws Exception {
		long roomId = testData.get(0).getRoomId() + testData.get(1).getRoomId();
		try {
			RoomPrice roomPrice = new RoomPrice();
			roomPrice.setRoomId(roomId);
			roomPrice.setPrice(50D);
			API.add(roomPrice);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.BAD_REQUEST, e.getResponse());
			ApplicationExceptionMessage message = getApplicationExceptionMessage(e);
			assertEquals(Status.BAD_REQUEST.getStatusCode(), message.getStatus());
			assertEquals("There is no room with [roomId=" + roomId + "]", message.getMessage());
		}
	}

	@Test
	public void testUpdate() throws Exception {
		Response response = API.add(testData.get(0));
		assertResponseStatus(Status.CREATED, response);

		response = API.updatePrice(testData.get(0).getRoomId(), testData.get(1).getPrice());
		assertResponseStatus(Status.NO_CONTENT, response);

		List<RoomPrice> results = API.get();
		assertEquals(1, results.size());
		assertEquals(new Long(1), API.count());
	}

	@Test
	public void testUpdatePriceOfNotPersistedEntityShouldReturnNotFound() throws Exception {
		long roomId = testData.get(0).getRoomId() + testData.get(1).getRoomId();
		try {
			API.updatePrice(roomId, 100D);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.NOT_FOUND, e.getResponse());
			ApplicationExceptionMessage message = getApplicationExceptionMessage(e);
			assertEquals(Status.NOT_FOUND.getStatusCode(), message.getStatus());
			assertEquals("No room price found with [roomId=" + roomId + "]", message.getMessage());
		}
	}

	@Test
	public void testRemoveEntity() throws Exception {
		Response response = API.add(testData.get(0));
		assertResponseStatus(Status.CREATED, response);

		response = API.remove(testData.get(0).getRoomId());
		assertResponseStatus(Status.NO_CONTENT, response);
		assertEquals(new Long(0), API.count());
	}

	@Test
	public void testRemoveOfNotPersistedEntityShouldReturnNotFound() throws Exception {
		Long roomId = testData.get(0).getRoomId() + testData.get(1).getRoomId();
		try {
			API.remove(roomId);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.NOT_FOUND, e.getResponse());
			ApplicationExceptionMessage message = getApplicationExceptionMessage(e);
			assertEquals(Status.NOT_FOUND.getStatusCode(), message.getStatus());
			assertEquals("No room price found with [roomId=" + roomId + "]", message.getMessage());
		}
	}

	@Test
	public void testGetSingleEntity() throws Exception {
		Response response = API.add(testData.get(0));
		assertResponseStatus(Status.CREATED, response);

		Double result = API.getPrice(testData.get(0).getRoomId());

		assertEquals(testData.get(0).getPrice(), result);
		assertEquals(new Long(1), API.count());
	}

	@Test
	public void testAddTwoEntities() throws Exception {
		for (RoomPrice next : testData) {
			Response response = API.add(next);
			assertResponseStatus(Status.CREATED, response);
		}

		List<RoomPrice> results = API.get();
		assertNotNull(results);
		assertEquals(2, results.size());
		for (int i = 0; i < testData.size(); i++) {
			assertRoomPriceEquals(testData.get(i), results.get(i));
		}

		assertEquals(new Long(2), API.count());
	}

	private void assertRoomPriceEquals(RoomPrice expected, RoomPrice actual) {
		assertNotNull(actual);
		assertEquals(expected.getRoomId(), actual.getRoomId());
		assertEquals(expected.getPrice(), actual.getPrice());
	}
}

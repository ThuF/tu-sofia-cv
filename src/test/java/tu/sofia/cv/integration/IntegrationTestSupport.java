package tu.sofia.cv.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import tu.sofia.cv.validation.ApplicationExceptionMessage;

@SuppressWarnings("javadoc")
public abstract class IntegrationTestSupport {

	private static final Gson GSON = new Gson();

	private static final String ENDPOINT = "http://localhost:" + System.getProperty("local.server.http.port") + "/cv/";
	private static final String ENDPOINT_API = ENDPOINT + "api/v1";
	private static final String ENDPOINT_LOGIN = ENDPOINT_API + "/protected/admin";

	private static final StringBuilder cookies = new StringBuilder();
	private static final LogLevel logLevel = RestAdapter.LogLevel.NONE;

	protected RestAdapter createRestAdapter() {
		return new RestAdapter.Builder().setRequestInterceptor(new RequestInterceptorHandler()).setEndpoint(ENDPOINT_API)
				.setConverter(new DateConverter()).setLogLevel(logLevel).build();
	}

	protected void login(UserRole userRole) {
		RestAdapter logingRestAdapter = new RestAdapter.Builder().setEndpoint(ENDPOINT_LOGIN).setLogLevel(logLevel).build();
		Response response = null;
		switch (userRole) {
			case EVERYONE:
				response = loginAsEveryone(logingRestAdapter);
				break;
			case ADMIN:
				response = loginAsAdminUser(logingRestAdapter);
				break;
		}
		saveCookies(response);
	}

	protected void logout() {
		cookies.delete(0, cookies.length());
	}

	protected ApplicationExceptionMessage getApplicationExceptionMessage(RetrofitError e) throws IOException {
		String json = IOUtils.toString(e.getResponse().getBody().in());
		return GSON.fromJson(json, ApplicationExceptionMessage.class);
	}

	protected long getResponseAsLong(Response response) throws IOException {
		return Long.parseLong(IOUtils.toString(response.getBody().in()));
	}

	protected void assertResponseStatus(Status expectedStatus, Response response) {
		assertEquals(expectedStatus.getStatusCode(), response.getStatus());
	}

	protected void assertErrorResponse(RetrofitError e, Status expectedStatus, String expectedMessage) throws IOException {
		assertResponseStatus(expectedStatus, e.getResponse());
		ApplicationExceptionMessage message = getApplicationExceptionMessage(e);
		assertEquals(expectedStatus.getStatusCode(), message.getStatus());
		assertEquals(expectedMessage, message.getMessage());
	}

	protected Date getDateOnly(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	private Response loginAsEveryone(RestAdapter logingRestAdapter) {
		Response response;
		try {
			EveryoneLoginService login = logingRestAdapter.create(EveryoneLoginService.class);
			// try to authenticate
			response = login.authenticate();
		} catch (RetrofitError e) {
			response = e.getResponse();
		}
		return response;
	}

	private Response loginAsAdminUser(RestAdapter logingRestAdapter) {
		Response response;
		try {
			AdminUserLoginService login = logingRestAdapter.create(AdminUserLoginService.class);
			// try to authenticate
			response = login.authenticate();
		} catch (RetrofitError e) {
			response = e.getResponse();
		}
		return response;
	}

	private static void saveCookies(final Response response) {
		assertNotNull("Response should not be null when saving cookies. Check request", response);
		List<Header> headers = response.getHeaders();
		for (final Header header : headers) {
			if ((header.getName() != null) && header.getName().equals("Set-Cookie")) {
				cookies.append(header.getValue());
				cookies.append(';');
			}
		}
	}

	private static class RequestInterceptorHandler implements RequestInterceptor {

		@Override
		public void intercept(final RequestFacade request) {
			request.addHeader("Cookie", cookies.toString());
		}
	}

	protected interface EveryoneLoginService {
		String username = "user";

		/**
		 * Authenticates with user name: user, password: user. Use any
		 * Base64Encoder("user:user")
		 **/
		@GET("/")
		@Headers({ "Accept: application/json", "Authorization: Basic dXNlcjp1c2Vy" })
		Response authenticate();
	}

	protected interface AdminUserLoginService {
		String username = "admin";

		/**
		 * Authenticates with user name: support-user, password: support. Use any
		 * Base64Encoder("support-user:support")
		 **/
		@GET("/")
		@Headers({ "Accept: application/json", "Authorization: Basic YWRtaW46YWRtaW4=" })
		Response authenticate();
	}
}

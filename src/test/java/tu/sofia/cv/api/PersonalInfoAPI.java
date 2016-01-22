package tu.sofia.cv.api;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import tu.sofia.cv.entity.PersonalInfo;

@SuppressWarnings("javadoc")
public interface PersonalInfoAPI {

	@GET("/protected/admin/personal-info")
	PersonalInfo get();

	@POST("/protected/admin/personal-info")
	@Headers("Accept: application/json")
	Response add(@Body final PersonalInfo body);

	@PUT("/protected/admin/personal-info")
	@Headers("Accept: application/json")
	Response update(@Body final PersonalInfo body);

	@DELETE("/protected/admin/personal-info")
	@Headers("Accept: application/json")
	Response delete();
}

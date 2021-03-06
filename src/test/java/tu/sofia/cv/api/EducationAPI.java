package tu.sofia.cv.api;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import tu.sofia.cv.entity.Education;

@SuppressWarnings("javadoc")
public interface EducationAPI {

	@GET("/protected/admin/educations")
	List<Education> get();

	@GET("/protected/admin/educations/{id}")
	Education get(@Path("id") final Long id);

	@POST("/protected/admin/educations")
	@Headers("Accept: application/json")
	Response add(@Body final Education body);

	@PUT("/protected/admin/educations/{id}")
	@Headers("Accept: application/json")
	Response update(@Path("id") final Long id, @Body final Education body);

	@DELETE("/protected/admin/educations/{id}")
	@Headers("Accept: application/json")
	Response delete(@Path("id") final Long id);
}

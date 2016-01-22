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
import tu.sofia.cv.entity.Project;

@SuppressWarnings("javadoc")
public interface ProjectAPI {

	@GET("/protected/admin/projects")
	List<Project> get();

	@GET("/protected/admin/projects/{id}")
	Project get(@Path("id") final Long id);

	@POST("/protected/admin/projects")
	@Headers("Accept: application/json")
	Response add(@Body final Project body);

	@PUT("/protected/admin/projects/{id}")
	@Headers("Accept: application/json")
	Response update(@Path("id") final Long id, @Body final Project body);

	@DELETE("/protected/admin/projects/{id}")
	@Headers("Accept: application/json")
	Response delete(@Path("id") final Long id);
}

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
import tu.sofia.cv.entity.Position;

@SuppressWarnings("javadoc")
public interface PositionAPI {

	@GET("/protected/admin/positions")
	List<Position> get();

	@GET("/protected/admin/positions/{id}")
	Position get(@Path("id") final Long id);

	@POST("/protected/admin/positions")
	@Headers("Accept: application/json")
	Response add(@Body final Position body);

	@PUT("/protected/admin/positions/{id}")
	@Headers("Accept: application/json")
	Response update(@Path("id") final Long id, @Body final Position body);

	@DELETE("/protected/admin/positions/{id}")
	@Headers("Accept: application/json")
	Response delete(@Path("id") final Long id);
}

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
import tu.sofia.cv.entity.Skill;

@SuppressWarnings("javadoc")
public interface SkillAPI {

	@GET("/protected/admin/skills")
	List<Skill> get();

	@GET("/protected/admin/skills/{id}")
	Skill get(@Path("id") final Long id);

	@POST("/protected/admin/skills")
	@Headers("Accept: application/json")
	Response add(@Body final Skill body);

	@PUT("/protected/admin/skills/{id}")
	@Headers("Accept: application/json")
	Response update(@Path("id") final Long id, @Body final Skill body);

	@DELETE("/protected/admin/skills/{id}")
	@Headers("Accept: application/json")
	Response delete(@Path("id") final Long id);
}

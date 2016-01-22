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
import tu.sofia.cv.entity.Company;

@SuppressWarnings("javadoc")
public interface CompanyAPI {

	@GET("/protected/admin/companies")
	List<Company> get();

	@GET("/protected/admin/companies/{id}")
	Company get(@Path("id") final Long id);

	@POST("/protected/admin/companies")
	@Headers("Accept: application/json")
	Response add(@Body final Company body);

	@PUT("/protected/admin/companies/{id}")
	@Headers("Accept: application/json")
	Response update(@Path("id") final Long id, @Body final Company body);

	@DELETE("/protected/admin/companies/{id}")
	@Headers("Accept: application/json")
	Response delete(@Path("id") final Long id);
}

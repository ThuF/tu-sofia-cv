package tu.sofia.cv.integration.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import retrofit.RetrofitError;
import retrofit.client.Response;
import tu.sofia.cv.api.ProjectAPI;
import tu.sofia.cv.entity.Project;
import tu.sofia.cv.integration.IntegrationTestSupport;
import tu.sofia.cv.integration.UserRole;

@SuppressWarnings("javadoc")
public class ProjectIntegrationTest extends IntegrationTestSupport {

	private ProjectAPI API;
	private Project expectedProject;
	private Project updatedProject;

	@Before
	public void setUp() throws Exception {
		expectedProject = createProject("CV Project");
		updatedProject = createProject("Bookings Project");

		API = createRestAdapter().create(ProjectAPI.class);
		login(UserRole.ADMIN);
	}

	private Project createProject(String name) {
		Project project = new Project();
		project.setName(name);
		return project;
	}

	@After
	public void cleanUp() throws Exception {
		logout();
		login(UserRole.ADMIN);

		removeProjects();

		logout();
	}

	private void removeProjects() {
		for (Project next : API.get()) {
			Response response = API.delete(next.getProjectId());
			assertResponseStatus(Status.NO_CONTENT, response);
		}
		assertTrue(API.get().isEmpty());
	}

	@Test
	public void testEmptyProjects() throws Exception {
		List<Project> results = API.get();
		assertNotNull(results);
		assertTrue(results.isEmpty());
	}

	@Test
	public void testAddProject() throws Exception {
		Response response = API.add(expectedProject);
		assertResponseStatus(Status.CREATED, response);
		expectedProject.setProjectId(getResponseAsLong(response));

		List<Project> results = API.get();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertProjectEquals(expectedProject, results.get(0));
	}

	@Test
	public void testAddTwoProjects() throws Exception {
		Response response = API.add(expectedProject);
		assertResponseStatus(Status.CREATED, response);
		expectedProject.setProjectId(getResponseAsLong(response));

		response = API.add(updatedProject);
		assertResponseStatus(Status.CREATED, response);
		updatedProject.setProjectId(getResponseAsLong(response));

		List<Project> results = API.get();
		assertNotNull(results);
		assertEquals(2, results.size());
		assertProjectEquals(expectedProject, results.get(0));
		assertProjectEquals(updatedProject, results.get(1));
	}

	@Test
	public void testAddProjectMissingName() throws Exception {
		expectedProject.setName(null);

		try {
			API.add(expectedProject);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [name] property can't be null");
		}
	}

	@Test
	public void testUpdateProject() throws Exception {
		Response response = API.add(expectedProject);
		assertResponseStatus(Status.CREATED, response);
		expectedProject.setProjectId(getResponseAsLong(response));
		updatedProject.setProjectId(expectedProject.getProjectId());

		response = API.update(expectedProject.getProjectId(), updatedProject);
		assertResponseStatus(Status.NO_CONTENT, response);

		assertProjectEquals(updatedProject, API.get(updatedProject.getProjectId()));
	}

	@Test
	public void testUpdateProjectWhenNoProjectIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.update(id, updatedProject);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no project with [projectId=" + id + "]");
		}
	}

	@Test
	public void testDeleteProject() throws Exception {
		Response response = API.add(expectedProject);
		assertResponseStatus(Status.CREATED, response);
		expectedProject.setProjectId(getResponseAsLong(response));

		response = API.delete(expectedProject.getProjectId());
		assertResponseStatus(Status.NO_CONTENT, response);

		testEmptyProjects();
	}

	@Test
	public void testRemoveProjectWhenNoProjectIsAdded() throws Exception {
		Long id = 1L;
		try {
			API.delete(id);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no project with [projectId=" + id + "]");
		}
	}

	@Test
	public void testForbidden() throws Exception {
		logout();
		login(UserRole.EVERYONE);

		try {
			API.add(expectedProject);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.FORBIDDEN, e.getResponse());
		}
	}

	private void assertProjectEquals(Project expected, Project actual) {
		assertEquals("The [projectId] properties are not equals", expected.getProjectId(), actual.getProjectId());
		assertEquals("The [name] properties are not equals", expected.getName(), actual.getName());
		assertEquals("The [description] properties are not equals", expected.getDescription(), actual.getDescription());
		assertEquals("The [url] properties are not equals", expected.getUrl(), actual.getUrl());
		assertEquals("The [icon] properties are not equals", expected.getIcon(), actual.getIcon());
		assertEquals("The [shouldDisplay] properties are not equals", expected.getIsPublic(), actual.getIsPublic());
	}
}

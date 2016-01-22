package tu.sofia.cv.integration.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import retrofit.RetrofitError;
import retrofit.client.Response;
import tu.sofia.cv.api.SkillAPI;
import tu.sofia.cv.entity.Skill;
import tu.sofia.cv.integration.IntegrationTestSupport;
import tu.sofia.cv.integration.UserRole;

@SuppressWarnings("javadoc")
public class SkillIntegrationTest extends IntegrationTestSupport {

	private SkillAPI API;
	private Skill expectedSkill;
	private Skill updatedSkill;

	@Before
	public void setUp() throws Exception {
		expectedSkill = createSkill("Java");
		updatedSkill = createSkill("OOP");

		API = createRestAdapter().create(SkillAPI.class);
		login(UserRole.ADMIN);
	}

	private Skill createSkill(String name) {
		Skill skill = new Skill();
		skill.setName(name);
		return skill;
	}

	@After
	public void cleanUp() throws Exception {
		logout();
		login(UserRole.ADMIN);

		removeSkills();

		logout();
	}

	private void removeSkills() {
		for (Skill next : API.get()) {
			Response response = API.delete(next.getSkillId());
			assertResponseStatus(Status.NO_CONTENT, response);
		}
		assertTrue(API.get().isEmpty());
	}

	@Test
	public void testEmptySkills() throws Exception {
		List<Skill> results = API.get();
		assertNotNull(results);
		assertTrue(results.isEmpty());
	}

	@Test
	public void testAddSkill() throws Exception {
		Response response = API.add(expectedSkill);
		assertResponseStatus(Status.CREATED, response);
		expectedSkill.setSkillId(getResponseAsLong(response));

		List<Skill> results = API.get();
		assertNotNull(results);
		assertEquals(1, results.size());
		assertSkillEquals(expectedSkill, results.get(0));
	}

	@Test
	public void testAddTwoSkills() throws Exception {
		Response response = API.add(expectedSkill);
		assertResponseStatus(Status.CREATED, response);
		expectedSkill.setSkillId(getResponseAsLong(response));

		response = API.add(updatedSkill);
		assertResponseStatus(Status.CREATED, response);
		updatedSkill.setSkillId(getResponseAsLong(response));

		List<Skill> results = API.get();
		assertNotNull(results);
		assertEquals(2, results.size());
		assertSkillEquals(expectedSkill, results.get(0));
		assertSkillEquals(updatedSkill, results.get(1));
	}

	@Test
	public void testAddSkillMissingName() throws Exception {
		expectedSkill.setName(null);

		try {
			API.add(expectedSkill);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.BAD_REQUEST, "The [name] property can't be null");
		}
	}

	@Test
	public void testUpdateSkill() throws Exception {
		Response response = API.add(expectedSkill);
		assertResponseStatus(Status.CREATED, response);
		expectedSkill.setSkillId(getResponseAsLong(response));

		response = API.update(expectedSkill.getSkillId(), updatedSkill);
		assertResponseStatus(Status.NO_CONTENT, response);

		assertSkillEquals(updatedSkill, API.get(expectedSkill.getSkillId()));
	}

	@Test
	public void testUpdateSkillWhenNoSkillIsAdded() throws Exception {
		Long skillId = new Random().nextLong();
		try {
			API.update(skillId, updatedSkill);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no skill with [skillId=" + skillId + "]");
		}
	}

	@Test
	public void testDeleteSkill() throws Exception {
		Response response = API.add(expectedSkill);
		assertResponseStatus(Status.CREATED, response);
		expectedSkill.setSkillId(getResponseAsLong(response));

		response = API.delete(expectedSkill.getSkillId());
		assertResponseStatus(Status.NO_CONTENT, response);

		testEmptySkills();
	}

	@Test
	public void testRemoveSkillWhenNoSkillIsAdded() throws Exception {
		Long skillId = new Random().nextLong();
		try {
			API.delete(skillId);
		} catch (RetrofitError e) {
			assertErrorResponse(e, Status.NOT_FOUND, "There is no skill with [skillId=" + skillId + "]");
		}
	}

	@Test
	public void testForbidden() throws Exception {
		logout();
		login(UserRole.EVERYONE);

		try {
			API.add(expectedSkill);
		} catch (RetrofitError e) {
			assertResponseStatus(Status.FORBIDDEN, e.getResponse());
		}
	}

	private void assertSkillEquals(Skill expected, Skill actual) {
		assertEquals("The [skillId] properties are not equals", expected.getSkillId(), actual.getSkillId());
		assertEquals("The [name] properties are not equals", expected.getName(), actual.getName());
	}
}

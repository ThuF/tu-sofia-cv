package tu.sofia.cv.service.proxy.publics;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.common.util.StringUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.entity.additional.SkillType;
import tu.sofia.cv.entity.dto.SkillGroupDto;
import tu.sofia.cv.service.SkillService;

/**
 * Public proxy for skills
 */
@Singleton
@Path("/public/skills")
public class SkillPublicProxy {

	private SkillService skillService;

	/**
	 * Constructor
	 *
	 * @param skillService
	 */
	@Inject
	public SkillPublicProxy(SkillService skillService) {
		this.skillService = skillService;
	}

	/**
	 * Returns a list of all skill groups
	 *
	 * @return a list of all skill groups
	 */
	@GET
	@Path("/groups")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SkillGroupDto> getSkillGroups() {
		List<SkillGroupDto> skillGroups = new ArrayList<SkillGroupDto>();
		for (SkillType next : SkillType.values()) {
			SkillGroupDto skillGroup = new SkillGroupDto(next, skillService.getSkills(next));
			if (!StringUtils.isEmpty(skillGroup.getHighlight())) {
				skillGroups.add(skillGroup);
			}
		}
		return skillGroups;
	}

	/**
	 * Returns the count of all skills
	 *
	 * @return the count of all skills
	 */
	@GET
	@Path("/count")
	public Long count() {
		return skillService.count();
	}
}

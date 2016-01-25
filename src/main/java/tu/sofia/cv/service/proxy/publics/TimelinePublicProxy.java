package tu.sofia.cv.service.proxy.publics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tu.sofia.cv.entity.Education;
import tu.sofia.cv.entity.Position;
import tu.sofia.cv.entity.dto.TimelineDto;
import tu.sofia.cv.service.EducationService;
import tu.sofia.cv.service.PositionService;

/**
 * Public proxy for timeline
 */
@Singleton
@Path("/public/timeline")
public class TimelinePublicProxy {

	private EducationService educationService;
	private PositionService positionService;

	/**
	 * Constructor
	 *
	 * @param educationService
	 * @param positionService
	 */
	@Inject
	public TimelinePublicProxy(EducationService educationService, PositionService positionService) {
		this.educationService = educationService;
		this.positionService = positionService;
	}

	/**
	 * Returns a list of all timeline events
	 *
	 * @return a list of all timeline events
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Set<TimelineDto> getTimeline() {
		Set<TimelineDto> timeline = new TreeSet<TimelineDto>(new Comparator<TimelineDto>() {

			@Override
			public int compare(TimelineDto o1, TimelineDto o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
		});
		timeline.addAll(getEducationsTimeline());
		timeline.addAll(getPositionsTimeline());
		return timeline;
	}

	private List<TimelineDto> getEducationsTimeline() {
		List<TimelineDto> educations = new ArrayList<TimelineDto>();
		for (Education next : educationService.getEducations()) {
			educations.add(new TimelineDto(next));
		}
		return educations;
	}

	private List<TimelineDto> getPositionsTimeline() {
		List<TimelineDto> positions = new ArrayList<TimelineDto>();
		for (Position next : positionService.getPositions()) {
			positions.add(new TimelineDto(next));
		}
		return positions;
	}

	/**
	 * Returns the count of all timeline events
	 *
	 * @return the count of all timeline events
	 */
	@GET
	@Path("/count")
	public Long count() {
		return educationService.count() + positionService.count();
	}
}

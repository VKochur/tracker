package team.mediasoft.education.tracker.service;

import team.mediasoft.education.tracker.dto.StoryPointInput;
import team.mediasoft.education.tracker.entity.StoryPoint;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.service.basic.FinderBasicService;
import team.mediasoft.education.tracker.support.Wrap;

import java.util.List;

public interface StoryPointService extends FinderBasicService<StoryPoint, Long> {

    /**
     * Create new storyPoint and edited related pack's state
     * @param dtoInput
     * @return
     */
    Wrap<StoryPoint, SurfaceException> addStoryPoint(StoryPointInput dtoInput);

    List<StoryPoint> getByIdsOrderByPoint(List<Long> ids);

}

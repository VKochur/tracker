package team.mediasoft.education.tracker.service;

import team.mediasoft.education.tracker.dto.StoryPointInput;
import team.mediasoft.education.tracker.entity.StoryPoint;

import java.util.List;

public interface StoryPointService
        extends BasicService<Long, StoryPoint, StoryPointInput> {

    List<StoryPoint> getByIdsOrderByPoint(List<Long> ids);

}

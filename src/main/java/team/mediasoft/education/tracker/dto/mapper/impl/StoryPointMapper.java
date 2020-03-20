package team.mediasoft.education.tracker.dto.mapper.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.dto.StoryPointOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.StoryPoint;


@Component
public class StoryPointMapper implements Mapper<StoryPoint, StoryPointOutput> {
    @Override
    public StoryPointOutput getOutput(StoryPoint entity) {
        StoryPointOutput output = new StoryPointOutput();
        output.setId(entity.getId());
        output.setPackId(entity.getPack().getId());
        output.setNodeId(entity.getPlace().getId());
        output.setState(entity.getState());
        output.setPoint(entity.getPoint());
        return output;
    }
}

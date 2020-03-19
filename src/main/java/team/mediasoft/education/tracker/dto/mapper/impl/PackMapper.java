package team.mediasoft.education.tracker.dto.mapper.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.dto.PackInput;
import team.mediasoft.education.tracker.dto.PackOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.StoryPoint;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PackMapper implements Mapper<Pack, PackOutput, PackInput> {

    @Override
    public PackOutput getOutput(Pack entity) {
        PackOutput packOutput = new PackOutput();
        packOutput.setId(entity.getId());
        packOutput.setType(entity.getType());
        packOutput.setIdentifier(entity.getIdentifier());
        packOutput.setRecipient(entity.getRecipient());
        packOutput.setDestinationId(entity.getDestination().getId());
        packOutput.setState(entity.getState());
        packOutput.setStoriesIds(storiesIds(entity));
        return null;
    }

    private List<Long> storiesIds(Pack entity) {
        List<StoryPoint> storyPoints = entity.getStoryPoints();
        return storyPoints.stream().map(storyPoint -> storyPoint.getId()).collect(Collectors.toList());
    }

}

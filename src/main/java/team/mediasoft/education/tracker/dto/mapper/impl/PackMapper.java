package team.mediasoft.education.tracker.dto.mapper.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.dto.PackOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.StoryPoint;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PackMapper implements Mapper<Pack, PackOutput> {

    @Override
    public PackOutput getOutput(Pack entity) {
        PackOutput packOutput = new PackOutput();
        packOutput.setId(entity.getId());
        packOutput.setTypeId(entity.getType().getId());
        packOutput.setIdentifier(entity.getIdentifier());
        packOutput.setRecipient(entity.getRecipient());
        packOutput.setDestinationId(entity.getDestination().getId());
        packOutput.setState(entity.getState());

        //todo: why isn't LasyException?
        packOutput.setStoriesIds(storiesIds(entity));

        return packOutput;
    }

    private List<Long> storiesIds(Pack entity) {
        List<StoryPoint> storyPoints = entity.getStoryPoints();
        return storyPoints.stream().map(storyPoint -> storyPoint.getId()).collect(Collectors.toList());
    }

}

package team.mediasoft.education.tracker.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.StoryPointInput;
import team.mediasoft.education.tracker.dto.StoryPointOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.StoryPoint;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.service.StoryPointService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/story_point")
public class StoryPointRest {

    private StoryPointService storyPointService;

    private Mapper<StoryPoint, StoryPointOutput> mapper;

    @Autowired
    public void setStoryPointService(StoryPointService storyPointService) {
        this.storyPointService = storyPointService;
    }

    @Autowired
    public void setMapper(Mapper<StoryPoint, StoryPointOutput> mapper) {
        this.mapper = mapper;
    }

    @PostMapping(produces = "application/json;charset=utf-8", consumes = "application/json;charset=utf-8")
    public StoryPointOutput createStoryPoint(@RequestBody StoryPointInput storyPointInput) throws SurfaceException {
        return mapper.getOutput(storyPointService.create(storyPointInput).getValueOrElseThrow());
    }

    @GetMapping(value = "/{id}")
    public StoryPointOutput getById(@PathVariable(name = "id") Long id) throws NotExistsDataException {
        Optional<StoryPoint> byId = storyPointService.getById(id);
        if (byId.isPresent()) {
            return mapper.getOutput(byId.get());
        } else {
            throw new NotExistsDataException("not found story's point by id = " + id);
        }
    }

    //todo: is it right?
    @PostMapping(value = "/ids")
    public List<StoryPointOutput> getListByIdsOrderedByHappened(@RequestBody List<Long> ids) {
        List<StoryPoint> byIdsOrderByPoint = storyPointService.getByIdsOrderByPoint(ids);
        return mapper.getListOutputs(byIdsOrderByPoint);
    }
}

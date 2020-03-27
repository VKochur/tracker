package team.mediasoft.education.tracker.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.PackInput;
import team.mediasoft.education.tracker.dto.PackOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.support.PackStates;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.service.PackService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("api/pack")
public class PackRest {

    private PackService packService;

    private Mapper<Pack, PackOutput> mapper;

    @Autowired
    public void setPackService(PackService packService) {
        this.packService = packService;
    }

    @Autowired
    public void setMapper(Mapper<Pack, PackOutput> mapper) {
        this.mapper = mapper;
    }

    @PostMapping(produces = "application/json;charset=utf-8")
    public PackOutput createPack(@Valid @RequestBody PackInput packInput) throws SurfaceException {
        return mapper.getOutput(packService.create(packInput).getValueOrElseThrow());
    }

    @GetMapping(value = "/{id}", produces = "application/json;charset=utf-8")
    public PackOutput getById(@PathVariable(name = "id") Long id) throws NotExistsDataException {
        Optional<Pack> byId = packService.getById(id);
        if (byId.isPresent()) {
            return mapper.getOutput(byId.get());
        } else {
            throw new NotExistsDataException("pack not found. id = " + id);
        }
    }

    @GetMapping(value = "identifier/{identifier}", produces = "application/json;charset=utf-8")
    public PackOutput getByIdentifier(@PathVariable(name = "identifier") String identifier) throws NotExistsDataException {
        Optional<Pack> byIdentifier = packService.getByIdentifier(identifier);
        if (byIdentifier.isPresent()) {
            return mapper.getOutput(byIdentifier.get());
        } else {
            throw new NotExistsDataException("pack not found. identifier = " + identifier);
        }
    }

    @GetMapping(value = "/available_states")
    public List<PackStates> getAvailablePackStates() {
        return Arrays.asList(PackStates.values());
    }

    @GetMapping(value = "/all")
    public List<PackOutput> getAllOrderByIdentifier(@Min(value = 0) @RequestParam("pageNumber") Integer pageNumber,
                                                  @Positive @RequestParam("pageSize") Integer pageSize) {
        return mapper.getListOutputs(packService.getAll(PageRequest.of(pageNumber, pageSize, Sort.by("identifier"))));
    }

    @GetMapping(value = "/with_loop_in_route")
    public List<Long> getIdsWhoseRouteHasLoop() {
        return packService.findPackIdsWhoseRouteHasLoop();
    }

    @GetMapping(value = "/were_in_node/{nodeId}")
    public List<Long> getIdsPackWhichWereInNode(@PathVariable(name = "nodeId") Long nodeId, @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to) {
        return packService.findPackIdsWhichWereInNodeAtTime(nodeId, from, to);
    }

    @DeleteMapping(value = "/{id}")
    public PackOutput deleteById(@PathVariable(name = "id") Long id) throws SurfaceException {
        return mapper.getOutput(packService.deleteById(id).getValueOrElseThrow());
    }


}

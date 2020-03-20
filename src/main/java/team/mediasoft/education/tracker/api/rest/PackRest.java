package team.mediasoft.education.tracker.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.PackInput;
import team.mediasoft.education.tracker.dto.PackOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.support.PackStates;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.service.PackService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public PackOutput createPack(@RequestBody PackInput packInput) throws SurfaceException {
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

}

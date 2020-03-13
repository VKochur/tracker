package team.mediasoft.education.tracker.api.rest.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.service.TypeService;
import team.mediasoft.education.tracker.support.Wrap;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type")
public class TypeRest {

    private TypeService typeService;

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping(value = "/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeDto getById(@PathVariable(name = "id") Long id) throws NotExistsDataException {
        Optional<TypeDto> byId = typeService.getById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new NotExistsDataException("type not found. id = " + id);
        }
    }

    @GetMapping(value = "/search/{typeName}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Long getIdByName(@PathVariable(name = "typeName") String typeName) throws NotExistsDataException {
        Optional<Long> id = typeService.getIdByNameIgnoreCase(typeName);
        if (id.isPresent()) {
            return id.get();
        } else {
            throw new NotExistsDataException("not found type by name = \"" + typeName + "\" (case of letters is ignored)");
        }
    }

    @PostMapping(value = "{typeName}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeDto createType(@PathVariable(name = "typeName") String typeName) throws SurfaceException {
        Wrap<TypeDto, SurfaceException> typeDtoWrap = typeService.create(typeName);
        return typeDtoWrap.getValueOrElseThrow();
    }

    @PutMapping(value = "/{id}/{newName}}", produces = "application/json;charset=utf-8")
    public TypeDto updateName(@PathVariable(name = "id") Long id, @PathVariable(name = "newName") String name) throws SurfaceException {
        Wrap<TypeDto, SurfaceException> typeDtoWrap = typeService.updateName(id, name);
        return typeDtoWrap.getValueOrElseThrow();
    }

    @DeleteMapping(value = "/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeDto deleteById(@PathVariable(name = "id") Long id) throws SurfaceException {
        Wrap<TypeDto, SurfaceException> typeDtoWrap = typeService.deleteById(id);
        return typeDtoWrap.getValueOrElseThrow();
    }

    @GetMapping(produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<TypeDto> findAll() {
        return typeService.getAllTypesOrderByName(true);
    }

}

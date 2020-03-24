package team.mediasoft.education.tracker.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.TypeInput;
import team.mediasoft.education.tracker.dto.TypeOutput;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.service.TypeService;
import team.mediasoft.education.tracker.support.Wrap;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/type")
public class TypeRest {

    private TypeService typeService;

    private Mapper<Type, TypeOutput> mapper;

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setMapper(Mapper<Type, TypeOutput> mapper) {
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeOutput getById(@PathVariable(name = "id") Long id) throws NotExistsDataException {
        Optional<Type> byId = typeService.getById(id);
        if (byId.isPresent()) {
            return mapper.getOutput(byId.get());
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

    @PostMapping(produces = "application/json;charset=utf-8", consumes = "application/json;charset=utf-8")
    @ResponseBody
    public TypeOutput createType(@Valid @RequestBody TypeInput typeInput) throws SurfaceException {
        Wrap<Type, SurfaceException> typeDtoWrap = typeService.create(typeInput);
        return mapper.getOutput(typeDtoWrap.getValueOrElseThrow());
    }

    @PutMapping(value = "/{id}/{newName}}", produces = "application/json;charset=utf-8")
    public TypeOutput updateName(@PathVariable(name = "id") Long id,
                                 @NotBlank(message = "name can't be empty") @Size(max = 50, message = "name's length must be in [1,50]")
                                 @PathVariable(name = "newName") String name) throws SurfaceException {
        Wrap<Type, SurfaceException> typeDtoWrap = typeService.updateName(id, name);
        return mapper.getOutput(typeDtoWrap.getValueOrElseThrow());
    }

    @DeleteMapping(value = "/{id}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeOutput deleteById(@PathVariable(name = "id") Long id) throws SurfaceException {
        Wrap<Type, SurfaceException> typeDtoWrap = typeService.deleteById(id);
        return mapper.getOutput(typeDtoWrap.getValueOrElseThrow());
    }

    @GetMapping(produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<TypeOutput> findAll() {
        return mapper.getListOutputs(typeService.getAllTypesOrderByName(true));
    }
}

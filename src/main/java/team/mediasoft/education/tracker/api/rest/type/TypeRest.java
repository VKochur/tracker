package team.mediasoft.education.tracker.api.rest.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.exception.tree.request.WrongInputDataException;
import team.mediasoft.education.tracker.sevice.TypeService;
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

    @PostMapping(value = "{typeName}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeDto createType(@PathVariable(name = "typeName") String typeName) throws WrongInputDataException {
        Wrap<TypeDto, WrongInputDataException> typeDtoWrap = typeService.create(typeName);
        return typeDtoWrap.getValueOrElseThrow();
    }

    @PutMapping(produces = "application/json;charset=utf-8")
    public TypeDto updateName(@RequestBody TypeInput typeInput) throws WrongInputDataException {
        Wrap<TypeDto, WrongInputDataException> typeDtoWrap = typeService.updateName(typeInput.getOldName(), typeInput.getNewName());
        return typeDtoWrap.getValueOrElseThrow();
    }

    @DeleteMapping(value = "/{typeName}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeDto deleteByName(@PathVariable(name = "typeName") String typeName) throws WrongInputDataException {
        Wrap<TypeDto, WrongInputDataException> typeDtoWrap = typeService.deleteByName(typeName);
        return typeDtoWrap.getValueOrElseThrow();
    }

    @GetMapping(produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<TypeDto> findAll() {
        return typeService.getAllOrderByName(true);
    }

}

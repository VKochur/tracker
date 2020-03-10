package team.mediasoft.education.tracker.api.rest.type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.exception.NotExistsDataException;
import team.mediasoft.education.tracker.exception.NotUniqueDataException;
import team.mediasoft.education.tracker.exception.WrongInputDataException;
import team.mediasoft.education.tracker.sevice.TypeService;

import java.util.List;

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
    public TypeDto getById(@PathVariable(name = "id") Long id) {
        try {
            return typeService.getById(id);
        } catch (NotExistsDataException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "{typeName}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeDto createType(@PathVariable(name = "typeName") String typeName) throws WrongInputDataException {
        return typeService.create(typeName);
    }

    @PutMapping(produces = "application/json;charset=utf-8")
    public TypeDto updateName(@RequestBody TypeInput typeInput) throws WrongInputDataException {
        return typeService.updateName(typeInput.getOldName(), typeInput.getNewName());
    }

    @DeleteMapping(value = "/{typeName}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TypeDto deleteByName(@PathVariable(name = "typeName") String typeName) throws WrongInputDataException {
        return typeService.deleteByName(typeName);
    }

    @GetMapping(produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<TypeDto> findAll() {
        return typeService.getAllOrderByName(true);
    }

}

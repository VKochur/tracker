package team.mediasoft.education.tracker.sevice;

import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.exception.WrongInputDataException;

import java.util.List;

public interface TypeService {

    TypeDto getByNameIgnoreCase(String typeName) throws WrongInputDataException;

    TypeDto create(String typeName) throws WrongInputDataException;

    TypeDto updateName(String oldName, String newName) throws WrongInputDataException;

    TypeDto deleteByName(String typeName) throws WrongInputDataException;

    List<TypeDto> getAllOrderByName(boolean asc);

}

package team.mediasoft.education.tracker.sevice;

import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.exception.tree.request.WrongInputDataException;
import team.mediasoft.education.tracker.support.Wrap;

import java.util.List;
import java.util.Optional;


public interface TypeService {

    /**
     * Gets dto for type with specific id, or Optional#empty() if not found
     * @param id must not be null
     * @return TypeDto for specific id
     * @throws IllegalArgumentException id is null
     */
    Optional<TypeDto> getById(Long id);

    /**
     *
     * @param id must not be null
     * @return true if type with specific id exists in storage, otherwise false
     * @throws IllegalArgumentException id is null
     */
    boolean isExisted(Long id);

    /**
     * Gets dto for type with specific name, or Optional#empty() if not found
     * @param typeName
     * @return TypeDto for specific typeName
     *
     */
    Optional<TypeDto> getByNameIgnoreCase(String typeName);

    /**
     *
     * @param typeName
     * @return container, that contains or result as TypeDto, or Exception, with added info about
     * reason why can't solve result
     */
    Wrap<TypeDto, WrongInputDataException> create(String typeName);

    Wrap<TypeDto, WrongInputDataException> updateName(String oldName, String newName);

    Wrap<TypeDto, WrongInputDataException> deleteByName(String typeName) throws WrongInputDataException;

    List<TypeDto> getAllOrderByName(boolean asc);

}

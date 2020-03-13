package team.mediasoft.education.tracker.service;

import team.mediasoft.education.tracker.dto.TypeDto;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.support.Wrap;

import java.util.List;
import java.util.Optional;


public interface TypeService {

    /**
     * Gets dto for type with specific id, or Optional#empty() if not found
     * @param id must not be null
     * @return TypeDto for specific id
     * @throws IllegalArgumentException if id is null
     */
    Optional<TypeDto> getById(Long id);

    /**
     *
     * @param typeName
     * @return type's id, with specific name (ignore case)
     */
    Optional<Long> getIdByNameIgnoreCase(String typeName);

    /**
     * Create type in storage
     *
     * @param typeName
     * @return container, that contains created type as TypeDto, or Exception, which contains info about
     * reason why can't get result
     */
    Wrap<TypeDto, SurfaceException> create(String typeName);

    /**
     * Rename existed type
     *
     * @param id
     * @param newName
     * @return container, that contains updated type as TypeDto, or Exception, which contains info about
     * reason why can't get result
     */
    Wrap<TypeDto, SurfaceException> updateName(Long id, String newName);

    /**
     * List all types in storage
     * @param asc
     * @return
     */
    List<TypeDto> getAllTypesOrderByName(boolean asc);

    /**
     * Delete existed type by id
     *
     * @param id
     * @return TypeDto for deleted type, or exception, which contains info about reason why can't delete type
     */
    Wrap<TypeDto, SurfaceException> deleteById(Long id);
}

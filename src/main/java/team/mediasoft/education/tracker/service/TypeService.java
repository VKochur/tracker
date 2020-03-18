package team.mediasoft.education.tracker.service;

import team.mediasoft.education.tracker.dto.TypeInput;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.support.Wrap;

import java.util.List;
import java.util.Optional;


public interface TypeService {

    /**
     * Gets type with specific id, or Optional#empty() if not found
     * @param id must not be null
     * @return type for specific id
     * @throws IllegalArgumentException if id is null
     */
    Optional<Type> getById(Long id);

    /**
     *
     * @param typeName
     * @return type's id, with specific name (ignore case)
     */
    Optional<Long> getIdByNameIgnoreCase(String typeName);

    /**
     * Create type in storage
     *
     * @param forCreation container of data for creation new entity in storage
     * @return container, that contains created type, or Exception, which contains info about
     * reason why can't get result
     */
    Wrap<Type, SurfaceException> create(TypeInput forCreation);

    /**
     * Rename existed type
     *
     * @param id
     * @param newName
     * @return container, that contains updated type, or Exception, which contains info about
     * reason why can't get result
     */
    Wrap<Type, SurfaceException> updateName(Long id, String newName);

    /**
     * List all types in storage
     * @param asc
     * @return
     */
    List<Type> getAllTypesOrderByName(boolean asc);

    /**
     * Delete existed type by id
     *
     * @param id
     * @return type for deleted type, or exception, which contains info about reason why can't delete type
     */
    Wrap<Type, SurfaceException> deleteById(Long id);
}

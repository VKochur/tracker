package team.mediasoft.education.tracker.service;

import team.mediasoft.education.tracker.dto.TypeInput;
import team.mediasoft.education.tracker.dto.TypeOutput;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.support.Wrap;

import java.util.List;
import java.util.Optional;


public interface TypeService
        extends BasicService<Long, Type, TypeOutput, TypeInput> {

    /**
     * @param typeName
     * @return type's id, with specific name (ignore case)
     */
    Optional<Long> getIdByNameIgnoreCase(String typeName);

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
     *
     * @param asc
     * @return
     */
    List<Type> getAllTypesOrderByName(boolean asc);

}

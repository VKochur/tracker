package team.mediasoft.education.tracker.service.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import java.util.Optional;

/**
 * Delete entity is similar for all entity
 *
 * Uses:
 *      JpaRepository<E, ID>. In order to work with storage
 *      WrapFactory<E, SurfaceException>. In order to get instance container with entity or exception inside
 *
 * @param <E> entity's type
 * @param <ID> entity's key's type
 */
public interface DeleterBasicService<E, ID> {

    WrapFactory<E, SurfaceException> wrapFactoryForDeleter();

    JpaRepository<E, ID> jpaRepositoryForDeleter();

    /**
     * Should return exception, if ability for delete entity doesn't exist
     * @param id entity's id
     * @return null if can delete entity with specific id, otherwise exception with reason
     */
    SurfaceException checkDeleteAbility(ID id);

    /**
     * Delete entity from storage
     * Checks ability before delete, so you have implement checkDeleteAbility
     * @param id
     * @return container, which contains or entity (that was deleted), or exception with reason why it didn't happen
     */
    default Wrap<E, SurfaceException> deleteById(ID id) {
        SurfaceException exception = checkDeleteAbility(id);
        if (exception != null) {
            return wrapFactoryForDeleter().ofFail(exception);
        } else {
            Optional<E> byId = jpaRepositoryForDeleter().findById(id);
            if (byId.isPresent()) {
                //delete
                jpaRepositoryForDeleter().deleteById(id);
                return wrapFactoryForDeleter().ofSuccess(byId.get());
            } else {
                return wrapFactoryForDeleter().ofFail(new NotExistsDataException("not found by id = " + id));
            }
        }
    }
}

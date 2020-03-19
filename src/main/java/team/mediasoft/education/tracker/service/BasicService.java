package team.mediasoft.education.tracker.service;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import java.util.Optional;

/**
 * Basic service.
 *
 * Uses:
 *      JpaRepository<E, ID>. In order to work with storage
 *      Mapper<E, D, I>. In order to get entity for creation by entityInput
 *      WrapFactory<E, SurfaceException>. In order to get instance container with entity or exception inside
 *
 * @param <ID> entity's id's type
 * @param <E> entity's type
 * @param <D> output dto's type
 * @param <I> input dto's type
 */
public interface BasicService<ID, E, D, I> {

    WrapFactory<E, SurfaceException> wrapFactory();

    JpaRepository<E, ID> jpaRepository();

    Mapper<E, D, I> dtoMapper();

    /**
     * Should return exception, if ability for delete entity doesn't exist
     * @param id entity's id
     * @return null if can delete entity with specific id, otherwise exception with reason
     */
    SurfaceException checkDeleteAbility(ID id);

    /**
     * Should return exception, if ability for creation entity doesnt exist
     * @param dtoInput data for creation entity
     * @return null if can create entity with specific data, otherwise exception with reason
     */
    SurfaceException checkCreateAbility(I dtoInput);

    default Optional<E> getById(ID id) {
        return jpaRepository().findById(id);
    }

    /**
     * Create entity into storage
     *
     * You can use default implementation for creation new entity,
     * if your mapper makes entity (forCreation = Mapper.getForCreation(entityInput)),
     * that you can pass into jpaRepository.save(forCreation) in order to create new entity.
     *
     * Checks ability before creation. You have implement checkCreateAbility
     *
     * If your mapper can't makes suitable entity by entityInput, you should override this method
     *
     * @param dtoInput
     * @return container, which contains or entity (that was created), or exception with reason why it didn't happen
     */
    default Wrap<E, SurfaceException> create(I dtoInput) {
        SurfaceException exception = checkCreateAbility(dtoInput);
        if (exception != null) {
            return wrapFactory().ofFail(exception);
        } else {
            E forCreation = dtoMapper().getForCreation(dtoInput);
            E saved = jpaRepository().save(forCreation);
            return wrapFactory().ofSuccess(saved);
        }
    }

    /**
     * Delete entity from storage
     *
     * Checks ability before delete. You have implement checkDeleteAbility
     *
     * If you aren't going to use delete, you can override this method and checkDeleteAbility
     * like this:
     *
     *     @Override
     *     public Wrap<E, SurfaceException> deleteById(ID id) {
     *         return wrapFactory.ofFail(new NotSupportedException("delete entity not supported"));
     *     }
     *
     *     @Override
     *     public SurfaceException checkDeleteAbility(ID aLong) {
     *         return new NotSupportedException("check delete not supported");
     *     }
     * and don't use them anywhere
     *
     * @param id
     * @return container, which contains or entity (that was deleted), or exception with reason why it didn't happen
     */
    default Wrap<E, SurfaceException> deleteById(ID id) {
        SurfaceException exception = checkDeleteAbility(id);
        if (exception != null) {
            return wrapFactory().ofFail(exception);
        } else {
            Optional<E> byId = jpaRepository().findById(id);
            if (byId.isPresent()) {
                //delete
                jpaRepository().deleteById(id);
                return wrapFactory().ofSuccess(byId.get());
            } else {
                return wrapFactory().ofFail(new NotExistsDataException("not found by id = " + id));
            }
        }
    }

}

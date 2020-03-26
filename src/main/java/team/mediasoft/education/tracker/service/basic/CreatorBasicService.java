package team.mediasoft.education.tracker.service.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

/**
 *
 * Creation is similar for all entities.
 *
 * Uses:
 *      JpaRepository<E, ID>. In order to work with storage
 *      WrapFactory<E, SurfaceException>. In order to get instance container with entity or exception inside
 *
 * @param <ID> entity's id's type
 * @param <E> entity's type
 * @param <I> input dto's type
 */
public interface CreatorBasicService<ID, E, I> {

    WrapFactory<E, SurfaceException> wrapFactoryForCreator();

    JpaRepository<E, ID> jpaRepositoryForCreator();

    /**
     * Should return exception, if ability for creation entity doesnt exist
     * @param dtoInput data for creation entity
     * @return null if can create entity with specific data, otherwise exception with reason
     */
    SurfaceException checkCreateAbility(I dtoInput);

    /**
     * Should make entity
     * that you can pass into jpaRepository.save(forCreation) in order to create new entity
     *
     * All verification should be in checkCreateAbility
     *
     * @param dtoInput
     * @return
     */
    E getEntityForCreationByInput(I dtoInput);

    /**
     * Creates entity into storage
     *
     * Uses this.getEntityForCreationByInput method's result
     * Checks ability before creation. You have to implement checkCreateAbility
     *
     * @param dtoInput
     * @return container, which contains or entity (that was created), or exception with reason why it didn't happen
     */
    default Wrap<E, SurfaceException> create(I dtoInput) {
        SurfaceException exception = checkCreateAbility(dtoInput);
        if (exception != null) {
            return wrapFactoryForCreator().ofFail(exception);
        } else {
            E forCreation = getEntityForCreationByInput(dtoInput);
            E saved = jpaRepositoryForCreator().save(forCreation);
            return wrapFactoryForCreator().ofSuccess(saved);
        }
    }
}

package team.mediasoft.education.tracker.service;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.dto.mapper.Mapper;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.exception.tree.request.NotExistsDataException;
import team.mediasoft.education.tracker.support.Wrap;
import team.mediasoft.education.tracker.support.WrapFactory;

import java.util.Optional;

/**
 * @param <ID> id
 * @param <E> entity
 * @param <D> dto output
 * @param <I> dto input
 */
public interface BasicService<ID, E, D, I> {

    WrapFactory<E, SurfaceException> wrapFactory();

    JpaRepository<E, ID> jpaRepository();

    Mapper<E, D, I> dtoMapper();

    SurfaceException checkDeleteAbility(ID id);

    SurfaceException checkCreateAbility(I dtoInput);

    default Optional<E> getById(ID id) {
        return jpaRepository().findById(id);
    }

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

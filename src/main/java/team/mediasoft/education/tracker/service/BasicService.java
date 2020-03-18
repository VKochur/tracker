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
 * @param <M> mapper
 */
public abstract class BasicService<ID, E, D, I, M> {

    private final WrapFactory<E, SurfaceException> wrapFactory;

    private JpaRepository<E, ID> jpaRepository;

    private Mapper<E, D, I> dtoMapper;

    public BasicService(JpaRepository<E, ID> jpaRepository, Mapper<E, D, I> mapper, WrapFactory<E, SurfaceException> wrapFactory) {
        this.jpaRepository = jpaRepository;
        this.dtoMapper = mapper;
        this.wrapFactory = wrapFactory;
    }

    public Optional<E> getById(ID id) {
        return jpaRepository.findById(id);
    }

    public Wrap<E, SurfaceException> create(I dtoInput) {
        SurfaceException exception = checkCreateAbility(dtoInput);
        if (exception != null) {
            return wrapFactory.ofFail(exception);
        } else {
            E forCreation = dtoMapper.getForCreation(dtoInput);
            E saved = jpaRepository.save(forCreation);
            return wrapFactory.ofSuccess(saved);
        }
    }

    public Wrap<E, SurfaceException> deleteById(ID id) {
        SurfaceException exception = checkDeleteAbility(id);
        if (exception != null) {
            return wrapFactory.ofFail(exception);
        } else {
            Optional<E> byId = jpaRepository.findById(id);
            if (byId.isPresent()) {
                //delete
                jpaRepository.deleteById(id);
                return wrapFactory.ofSuccess(byId.get());
            } else {
                return wrapFactory.ofFail(new NotExistsDataException("not found by id = " + id));
            }
        }
    }

    protected abstract SurfaceException checkDeleteAbility(ID id);

    protected abstract SurfaceException checkCreateAbility(I dtoInput);

}

package team.mediasoft.education.tracker.service.basic;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

/**
 * Finds entity, uses JpaRepository
 * @param <E> entity's type
 * @param <ID> entity's key's type
 */
public interface FinderBasicService<E, ID> {

    JpaRepository<E, ID> jpaRepositoryForFinder();

    default Optional<E> getById(ID id) {
        return jpaRepositoryForFinder().findById(id);
    }

    /**
     *
     * @param pageable
     * @return all entities
     */
    default List<E> getAll(Pageable pageable) {
        return jpaRepositoryForFinder().findAll(pageable).getContent();
    }

}

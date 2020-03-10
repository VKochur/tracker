package team.mediasoft.education.tracker.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.entity.Type;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findByNameIgnoreCase(String name);

    @Override
    List<Type> findAll(Sort sort);
}

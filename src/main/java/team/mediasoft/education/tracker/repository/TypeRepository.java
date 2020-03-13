package team.mediasoft.education.tracker.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.repository.custom.TypeRepositoryCustom;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long>, TypeRepositoryCustom {

    @Override
    List<Type> findAll(Sort sort);
}

package team.mediasoft.education.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.Type;
import team.mediasoft.education.tracker.repository.custom.PackRepositoryCustom;

import java.util.Optional;


public interface PackRepository extends JpaRepository<Pack, Long>, PackRepositoryCustom {

    boolean existsPackByType(Type type);

    Optional<Pack> findByIdentifier(String identifier);
}

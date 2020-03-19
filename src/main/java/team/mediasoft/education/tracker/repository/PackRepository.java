package team.mediasoft.education.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.entity.Type;

import java.util.Optional;


public interface PackRepository extends JpaRepository<Pack, Long> {

    boolean existsPackByType(Type type);

    Optional<Pack> findByIdentifier(String identifier);
}

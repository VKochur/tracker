package team.mediasoft.education.tracker.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.entity.Node;

import java.util.List;
import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, Long> {

    Optional<Node> findByPostcode(String postcode);

    List<Node> findByPostcodeStartingWithOrderByPostcode(String postcode, Pageable pageable);
}

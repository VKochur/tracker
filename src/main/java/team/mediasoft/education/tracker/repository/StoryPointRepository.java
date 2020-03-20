package team.mediasoft.education.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.mediasoft.education.tracker.entity.StoryPoint;

import java.util.Collection;
import java.util.List;

public interface StoryPointRepository extends JpaRepository<StoryPoint, Long> {

    List<StoryPoint> getByIdInOrderByPoint(Collection<Long> ids);
}

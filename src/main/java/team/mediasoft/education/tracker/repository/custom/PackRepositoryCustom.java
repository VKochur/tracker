package team.mediasoft.education.tracker.repository.custom;

import java.time.LocalDateTime;
import java.util.List;

public interface PackRepositoryCustom {

    List<Long> findPackIdsWhoseRouteHasLoop();

    List<Long> findPackIdsWhichWereInNodeAtTime(Long nodeId, LocalDateTime from, LocalDateTime to);
}

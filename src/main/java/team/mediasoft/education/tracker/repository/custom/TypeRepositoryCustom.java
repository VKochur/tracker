package team.mediasoft.education.tracker.repository.custom;

import java.util.Optional;

public interface TypeRepositoryCustom {

    Optional<Long> findIdByNameIgnoreCase(String typeName);

}

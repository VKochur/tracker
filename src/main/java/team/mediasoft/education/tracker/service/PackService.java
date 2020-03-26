package team.mediasoft.education.tracker.service;

import team.mediasoft.education.tracker.dto.PackInput;
import team.mediasoft.education.tracker.entity.Pack;
import team.mediasoft.education.tracker.service.basic.CreatorBasicService;
import team.mediasoft.education.tracker.service.basic.DeleterBasicService;
import team.mediasoft.education.tracker.service.basic.FinderBasicService;

import java.util.Optional;


public interface PackService extends
        CreatorBasicService<Long, Pack, PackInput>,
        FinderBasicService<Pack, Long>,
        DeleterBasicService<Pack, Long> {

    Optional<Pack> getByIdentifier(String identifier);
}

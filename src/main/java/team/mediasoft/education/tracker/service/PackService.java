package team.mediasoft.education.tracker.service;

import team.mediasoft.education.tracker.dto.PackInput;
import team.mediasoft.education.tracker.entity.Pack;

import java.util.Optional;


public interface PackService
        extends BasicService <Long, Pack, PackInput>{


    Optional<Pack> getByIdentifier(String identifier);
}

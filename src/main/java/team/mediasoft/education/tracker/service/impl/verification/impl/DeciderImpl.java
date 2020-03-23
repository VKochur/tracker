package team.mediasoft.education.tracker.service.impl.verification.impl;

import org.springframework.stereotype.Component;
import team.mediasoft.education.tracker.exception.SurfaceException;
import team.mediasoft.education.tracker.service.impl.verification.Decider;

import java.util.List;
import java.util.Optional;

/**
 * Returns first not null exception from list
 */
@Component
public class DeciderImpl implements Decider<SurfaceException> {

    @Override
    public SurfaceException decide(List<SurfaceException> exceptions) {
        Optional<SurfaceException> first = exceptions.stream().filter(e -> e != null).findFirst();
        return first.orElse(null);
    }
}

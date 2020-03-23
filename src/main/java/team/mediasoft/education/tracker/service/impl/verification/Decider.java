package team.mediasoft.education.tracker.service.impl.verification;

import java.util.List;

/**
 * Assembles info from all exception into one
 * @param <E>
 */
public interface Decider<E extends Exception> {

    /**
     * must
     *  1. return exception if exists at least one exception in list != null
     *  2. return null otherwise
     * @param exceptions
     * @return
     */
    E decide(List<E> exceptions);
}

package team.mediasoft.education.tracker.support;

import org.springframework.stereotype.Service;

/**
 * Produces Wraps, that are containers for info, that some method did
 * @param <D> value's type, that method could return
 * @param <E> exceptions's type, that method could throw
 */
@Service
public class WrapFactory<D, E extends Exception> {

    /**
     * @param value
     * @return return wrap for situation, when method completed successfully, and returns some value
     */
    public Wrap<D, E> ofSuccess(D value) {
        Wrap<D, E> wrap = new Wrap<>();
        wrap.setValue(value);
        return wrap;
    }

    /**
     * @param e
     * @return return wrap for situation, when method completed badly, and throws some Exception
     */
    public Wrap<D, E> ofFail(E e) {
        Wrap<D, E> wrap = new Wrap<>();
        wrap.setException(e);
        return wrap;
    }

}

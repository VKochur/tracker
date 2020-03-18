package team.mediasoft.education.tracker.exception.tree.inner;

import org.springframework.http.HttpStatus;
import team.mediasoft.education.tracker.exception.SurfaceException;

/**
 * Exception, which is caused because some action not supported
 */
public class NotSupportedException extends SurfaceException {

    private static final HttpStatus status = HttpStatus.NOT_IMPLEMENTED;

    public NotSupportedException(String message) {
        super(message, status);
    }
}

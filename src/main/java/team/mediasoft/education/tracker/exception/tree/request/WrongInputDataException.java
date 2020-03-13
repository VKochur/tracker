package team.mediasoft.education.tracker.exception.tree.request;

import org.springframework.http.HttpStatus;
import team.mediasoft.education.tracker.exception.SurfaceException;

/**
 * Exceptions which are caused because user specifies wrong data
 */
public abstract class WrongInputDataException extends SurfaceException {

    public static final HttpStatus HTTP_STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    public WrongInputDataException(String message) {
        super(message, HTTP_STATUS);
    }
}

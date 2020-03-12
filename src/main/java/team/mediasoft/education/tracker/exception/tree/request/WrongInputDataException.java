package team.mediasoft.education.tracker.exception.tree.request;

import org.springframework.http.HttpStatus;
import team.mediasoft.education.tracker.exception.ErrorResponse;
import team.mediasoft.education.tracker.exception.SurfaceException;

/**
 * Exceptions which are caused because user specifies wrong data
 */
public abstract class WrongInputDataException extends SurfaceException {

    public WrongInputDataException() {
    }

    public WrongInputDataException(String message) {
        super(message);
    }

    public WrongInputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongInputDataException(Throwable cause) {
        super(cause);
    }

    @Override
    public HttpStatus getRelatedStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public ErrorResponse getRelatedResponse() {
        return new ErrorResponse(getRelatedStatus().value(), this.getMessage());
    }
}

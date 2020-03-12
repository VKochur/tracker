package team.mediasoft.education.tracker.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception, that can be surface for response.
 * It may be exceptions related with specified users data, not available services and others
 *
 * subclasses should override methods getRelatedStatus and getRelatedResponse
 */
public class SurfaceException extends Exception {

    public static final HttpStatus HTTP_STATUS_DEFAULT = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final ErrorResponse ERROR_RESPONSE_DEFAULT = new ErrorResponse(HTTP_STATUS_DEFAULT.value(), "no info", "default message");

    public SurfaceException() {

    }

    public SurfaceException(String message) {
        super(message);
    }

    public SurfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SurfaceException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getRelatedStatus() {
        return HTTP_STATUS_DEFAULT;
    }

    public ErrorResponse getRelatedResponse() {
        return ERROR_RESPONSE_DEFAULT;
    }
}

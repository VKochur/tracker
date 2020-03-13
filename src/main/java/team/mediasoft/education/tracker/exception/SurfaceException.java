package team.mediasoft.education.tracker.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception, that can be surface for response.
 * It may be exceptions related with specified users data, not available services and others
 */
public abstract class SurfaceException extends Exception {

    public static final HttpStatus HTTP_STATUS_DEFAULT = HttpStatus.INTERNAL_SERVER_ERROR;

    private HttpStatus status;

    protected SurfaceException(String message, HttpStatus status) {
        super(message);
        this.status = status != null ? status : HTTP_STATUS_DEFAULT;
    }

    public HttpStatus getRelatedStatus() {
        return this.status;
    }

    public ErrorResponse getRelatedResponse() {
        return new ErrorResponse(this.getRelatedStatus().value(), this.getMessage());
    }
}

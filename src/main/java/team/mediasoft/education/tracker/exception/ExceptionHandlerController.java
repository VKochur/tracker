package team.mediasoft.education.tracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * Handles SurfaceExceptions.
     * uses response and status specified in exception for answer
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = SurfaceException.class)
    protected ResponseEntity<Object> handleNotUniqueConflict(SurfaceException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getRelatedResponse(), new HttpHeaders(), ex.getRelatedStatus(), request);
    }
}

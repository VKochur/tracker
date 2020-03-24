package team.mediasoft.education.tracker.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

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
    protected ResponseEntity<Object> handleSurfaceException(SurfaceException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getRelatedResponse(), new HttpHeaders(), ex.getRelatedStatus(), request);
    }

    /**
     * Handles MethodArgumentNotValidExceptions.
     * uses HttpStatus.BAD_REQUEST, info about errors from exception for response
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder message = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            message.append(fieldName).append(": ").append(errorMessage).append("; ");
        });

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message.toString().trim());
        return super.handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles ConstraintViolationExceptions.
     * uses HttpStatus.BAD_REQUEST, info about errors from exception for response
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        StringBuilder message = new StringBuilder();
        ex.getConstraintViolations().forEach(constraintViolation -> message.append(constraintViolation.getMessage()).append("; "));

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message.toString().trim());
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}

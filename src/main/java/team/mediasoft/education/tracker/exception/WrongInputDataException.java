package team.mediasoft.education.tracker.exception;

/**
 * Exceptions which are caused because user specifies wrong data
 */
public abstract class WrongInputDataException extends Exception {

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
}

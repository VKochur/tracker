package team.mediasoft.education.tracker.exception;

/**
 * Exception which is caused, because user specified data in wrong format
 */
public class NotValidDataException extends WrongInputDataException {
    public NotValidDataException(String message) {
        super(message);
    }
}

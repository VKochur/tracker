package team.mediasoft.education.tracker.exception;

/**
 * Exception which is caused, because action breaks rule, that some data must be unique
 */
public class NotUniqueDataException extends WrongInputDataException {
    public NotUniqueDataException(String message) {
        super(message);
    }
}

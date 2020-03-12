package team.mediasoft.education.tracker.exception.tree.request;

/**
 * Exception which is caused, because action requires some data, that doesn't exist
 */
public class NotExistsDataException extends WrongInputDataException {
    public NotExistsDataException(String message) {
        super(message);
    }
}

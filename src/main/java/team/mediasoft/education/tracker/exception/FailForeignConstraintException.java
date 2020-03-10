package team.mediasoft.education.tracker.exception;

/**
 * Exception which is caused, because action breaks foreign constrain
 */
public class FailForeignConstraintException extends WrongInputDataException {
    public FailForeignConstraintException(String message) {
        super(message);
    }
}

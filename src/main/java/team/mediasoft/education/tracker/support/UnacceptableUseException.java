package team.mediasoft.education.tracker.support;

/**
 * Exception which is caused, if try use some logic in wrong place
 */
public class UnacceptableUseException extends RuntimeException {
    public UnacceptableUseException(String s) {
        super(s);
    }
}

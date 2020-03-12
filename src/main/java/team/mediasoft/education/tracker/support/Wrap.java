package team.mediasoft.education.tracker.support;

/**
 * Container for result or exception which method returns or throw.
 *
 * Method can return value, or throw exception.
 * In order not throw exception we can use Wrap as returned value for method,
 * and specify value if method solved ok, otherwise exception, that can give info about reason of the fail
 *
 * For instance Wrap should use WrapFactory
 *
 * @param <D> value's type
 * @param <E> exception's type
 */
public class  Wrap<D, E extends Exception> {

    private D value;
    private E exception;

    /**
     * package access level for WrapFactory
     */
    Wrap() { }

    public boolean wasException () {
        return exception != null;
    }

    public boolean wasReturnedValue() {
        return exception == null;
    }

    public boolean wasReturnedNull() {
        if (exception == null) {
            return value == null;
        } else {
            return false;
        }
    }

    public boolean wasReturnedNotNull() {
        if (exception == null) {
            return value != null;
        } else {
            return false;
        }
    }

    /**
     * Use this method with check wasReturnedValue() before.
     * otherwise if value wasn't returned throws UnacceptableUseException
     * @return
     * @throws UnacceptableUseException if try get value, but exception was occurred
     */
    public D getValue() throws UnacceptableUseException {
        if (wasException()) {
            throw new UnacceptableUseException(
                    "try to use value, though exception was occurred: "
                    + exception.getClass().getSimpleName()
                    +", exception's message: " + exception.getMessage());
        } else {
            return value;
        }
    }

    /**
     * Get value, if exception wasn't.
     * If exception was, then throw it
     * @return
     * @throws E
     */
    public D getValueOrElseThrow() throws E {
        if (exception == null) {
            return value;
        } else {
            throw exception;
        }
    }

    public E getException() {
        return exception;
    }

    void setValue(D value) {
        this.value = value;
    }

    void setException(E exception) {
        this.exception = exception;
    }
}

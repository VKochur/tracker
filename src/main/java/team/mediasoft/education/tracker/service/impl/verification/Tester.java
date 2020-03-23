package team.mediasoft.education.tracker.service.impl.verification;

import java.util.function.Function;

/**
 * Check something.
 * If something is right, method "Function.apply" should return null,
 * otherwise exception with additional info about reason why it'is wrong
 * @param <Y>
 */
public interface Tester<Y extends Exception> extends Function<Object, Y> {

}

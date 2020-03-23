package team.mediasoft.education.tracker.service.impl.verification;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Solves result of testing
 * @param <Y>
 */
@Component
public class TestResultSolver<Y extends Exception> {

    private ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * @param testerClazz tester. must be spring's  component
     * @param arg something, that are testing
     * @return null, if test is ok, exception otherwise
     * @throws NoSuchBeanDefinitionException   if no bean of the given tester was found
     * @throws NoUniqueBeanDefinitionException if more than one bean of the tester type was found
     * @throws BeansException                  if the tester bean could not be created
     * @throws ClassCastException              if arg has type that isn't suitable for specified tester
     */
    public Y solve(Class<? extends Tester<? extends Y>> testerClazz, Object arg) {
        Tester<? extends Y> bean = context.getBean(testerClazz);
        return bean.apply(arg);
    }
}

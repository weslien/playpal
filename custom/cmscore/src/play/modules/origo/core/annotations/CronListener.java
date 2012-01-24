package play.modules.origo.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: gustav
 * Date: 2011-12-07
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CronListener {
    /**
     * @return
     */
    int secondsBetweenExecutions() default 86400;

    /**
     * @return
     */
    int secondsUntilFirstExecution() default 0;


}

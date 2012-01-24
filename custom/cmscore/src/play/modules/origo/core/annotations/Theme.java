package play.modules.origo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker for a class that has \@ThemeVariant method(s). A class annotated must have at least 1 method
 * annotated with \@ThemeVariant as well.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Theme {

    /**
     * A unique id for referencing this theme. Used as the key when caching this theme.
     *
     * @return a unique id for this theme.
     */
    String id();

}

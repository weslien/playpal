package play.modules.cmscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds a variant for a theme, the method it annotates returns a String path to a template.
 * Only valid in a class annotated by \@Theme.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ThemeVariant {
    
    String[] contentAreas();
    
}

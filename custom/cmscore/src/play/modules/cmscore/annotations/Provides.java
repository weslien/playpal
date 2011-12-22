package play.modules.cmscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds a new LeafType to the system. A method annotated with \@Provides will
 * be called to instantiate a new instance of this type each time a leaf of
 * this type is loaded from the database.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Provides {

    // TODO: Should this be a string instead with a unique code for each type?
    Class type();
    
}

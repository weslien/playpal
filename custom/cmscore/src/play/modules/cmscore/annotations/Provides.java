package play.modules.cmscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with \@Provides will be called to instantiate a new instance of this type each time
 * a rootLeaf of this type is loaded from the database.
 *
 * When type=LEAF it adds a new Leaf to the system.
 * When type=FORM it adds a form to edit a Leaf type.
 * When type=BLOCK it adds a way to look up content mapped to a page.
 * @see play.modules.cmscore.Leaf
 * @see play.modules.cmscore.ui.UIElement
 * @see models.cmscore.Block
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Provides {

    ProvidesType type();
    
    Class with();
    
}

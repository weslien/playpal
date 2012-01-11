package play.modules.cmscore.annotations;

import play.modules.cmscore.Leaf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Called before a rootLeaf is loaded. Modules/Add-ons/Plugins can use this to modify a rootLeaf.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface BeforeLeafLoaded {

    // TODO: Should this be a string instead with a unique code for each type?
    Class<? extends Leaf> type();

}

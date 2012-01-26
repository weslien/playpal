package play.modules.origo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Called before or after a rootNode is loaded. Modules/Add-ons/Plugins can use this to modify a rootNode.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OnLoad {

    Type type();

    String with() default "";

    boolean after() default true;

    public static enum Type {
        NODE, FORM, NAVIGATION, NAVIGATION_ITEM
    }
}
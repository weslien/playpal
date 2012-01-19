package play.modules.cmscore.annotations;

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

    Class with() default Object.class;

    boolean after() default true;

    public static enum Type {
        NODE, SEGMENT, FORM, NAVIGATION, NAVIGATION_ITEM
    }
}

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

    String type();

    String with() default "";

    boolean after() default true;

    public static final String NODE = "node";
    public static final String FORM = "form";
    public static final String NAVIGATION = "navigation";
    public static final String NAVIGATION_ITEM = "navigation_item";
}

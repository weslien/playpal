package play.modules.origo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with \@Provides will be called to instantiate a new instance of this type each time
 * a rootNode of this type is loaded from the database.
 * <p/>
 * When type=NODE it adds a new Node to the system.
 * When type=FORM it adds a form to edit a Node type.
 * When type=SEGMENT it adds a way to look up content mapped to a page.
 * When type=NAVIGATION it adds a different type of navigation than the standard one.
 * When type=NAVIGATION_ITEM it adds a navigation item to the current navigation type.
 *
 * @see play.modules.origo.core.Node
 * @see play.modules.origo.core.ui.UIElement
 * @see models.origo.core.Segment
 * @see models.origo.core.navigation.BasicNavigation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Provides {

    String type();

    String with();

    public static final String TYPE_NODE = "node";
    public static final String TYPE_FORM = "form";
    public static final String TYPE_NAVIGATION = "navigation";
    public static final String TYPE_NAVIGATION_ITEM = "navigation_item";
}

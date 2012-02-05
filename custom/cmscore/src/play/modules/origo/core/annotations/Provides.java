package play.modules.origo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method annotated with \@Provides will be called to instantiate a new instance of this type each time
 * a rootNode of this type is loaded from the database.
 * <p/>
 * <p/>
 * A method annotated with \@Provides should return the type it provides as listed below:
 * When type=NODE it adds a new Node to the system and the method should return a Node.
 * When type=FORM it adds a form to edit a Node type and the method should return a UIElement.
 * When type=NAVIGATION it adds a different type of navigation than the standard one and the method should return a NavigationElement.
 * When type=NAVIGATION_ITEM it adds a navigation item to the current navigation type and the method should return a NavigationElement.
 *
 * @see play.modules.origo.core.Node
 * @see play.modules.origo.core.ui.UIElement
 * @see models.origo.core.navigation.BasicNavigation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Provides {

    String type();

    String with();

    public static final String FORM = "form";
    public static final String NODE = "node";
    public static final String NAVIGATION = "navigation";
    public static final String NAVIGATION_ITEM = "navigation_item";

}

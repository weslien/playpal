package play.modules.cmscore.annotations;

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
 * @see play.modules.cmscore.Node
 * @see play.modules.cmscore.ui.UIElement
 * @see models.cmscore.Segment
 * @see models.cmscore.navigation.BasicNavigation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Provides {

    Type type();

    String with();

    public enum Type {
        NODE, SEGMENT, FORM, NAVIGATION, NAVIGATION_ITEM
    }

}

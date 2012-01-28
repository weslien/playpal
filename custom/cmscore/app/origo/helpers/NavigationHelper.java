package origo.helpers;

import play.modules.origo.core.Navigation;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.NavigationElement;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NavigationHelper {

    public static Collection<NavigationElement> getNavigation(Node node, String section) {
        String navigationType = SettingsHelper.getNavigationType();
        return triggerProvidesNavigationListener(navigationType, node, section);
    }

    /*
     * Convenience methods for hooks with NAVIGATION type
     */
    public static Collection<NavigationElement> triggerProvidesNavigationListener(String withType, Node node, String section) {
        return ProvidesHelper.triggerListener(Provides.NAVIGATION, withType, node, Collections.<Class, Object>singletonMap(String.class, section));
    }

    public static void triggerBeforeNavigationLoaded(String withType, Node node, Collection<NavigationElement> navigationElements, String section) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        //TODO: Figure out how to do this with a complete type Collection<NavigationElement>.class? instead of Collection.class
        args.put(Collection.class, navigationElements);
        args.put(String.class, section);
        OnLoadHelper.triggerBeforeListener(OnLoad.TYPE_NAVIGATION, withType, node, args);
    }

    public static void triggerAfterNavigationLoaded(String withType, Node node, Collection<NavigationElement> navigationElements, String section) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        //TODO: Figure out how to do this with a complete type Collection<NavigationElement>.class? instead of Collection.class
        args.put(Collection.class, navigationElements);
        args.put(String.class, section);
        OnLoadHelper.triggerAfterListener(OnLoad.TYPE_NAVIGATION, withType, node, args);
    }

    /*
     * Convenience methods for hooks with NAVIGATION_ITEM type
     */
    public static NavigationElement triggerProvidesNavigationItemListener(String withType, Node node, Navigation navigation) {
        return ProvidesHelper.triggerListener(Provides.NAVIGATION_ITEM, withType, node, Navigation.class, navigation);
    }

    public static NavigationElement triggerProvidesNavigationItemListener(String withType, Node node, Navigation navigation, NavigationElement navigationElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(Navigation.class, navigation);
        args.put(NavigationElement.class, navigationElement);
        return ProvidesHelper.triggerListener(Provides.NAVIGATION_ITEM, withType, node, args);
    }

    public static void triggerBeforeNavigationItemLoaded(String withType, Node node, Navigation navigation) {
        OnLoadHelper.triggerBeforeListener(OnLoad.TYPE_NAVIGATION_ITEM, withType, node, Navigation.class, navigation);
    }

    public static void triggerAfterNavigationItemLoaded(String withType, Node node, Navigation navigation, NavigationElement navigationElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(Navigation.class, navigation);
        args.put(NavigationElement.class, navigationElement);
        OnLoadHelper.triggerAfterListener(OnLoad.TYPE_NAVIGATION_ITEM, withType, node, args);
    }

}

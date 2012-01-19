package helpers;

import play.modules.cmscore.Navigation;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.OnLoad;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.ui.NavigationElement;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NavigationHelper {

    public static Collection<NavigationElement> getNavigation(Node node, String section) {
        Class navigationType = SettingsHelper.getNavigationType();
        return triggerProvidesNavigationListener(navigationType, node, section);
    }

    /*
     * Convenience methods for hooks with NAVIGATION type
     */
    public static Collection<NavigationElement> triggerProvidesNavigationListener(Class withType, Node node, String section) {
        return ProvidesHelper.triggerListener(Provides.Type.NAVIGATION, withType, node, Collections.<Class, Object>singletonMap(String.class, section));
    }

    public static void triggerBeforeNavigationLoaded(Class withType, Node node, Collection<NavigationElement> navigationElements) {
        //TODO: Figure out how to do this with a complete type Collection<NavigationElement>.class? instead of Collection.class
        OnLoadHelper.triggerBeforeListener(OnLoad.Type.NAVIGATION, withType, node, Collection.class, navigationElements);
    }

    public static void triggerAfterNavigationLoaded(Class withType, Node node, Collection<NavigationElement> navigationElements) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        //TODO: Figure out how to do this with a complete type Collection<NavigationElement>.class? instead of Collection.class
        args.put(Collection.class, navigationElements);
        OnLoadHelper.triggerAfterListener(OnLoad.Type.NAVIGATION, withType, node, args);
    }

    /*
     * Convenience methods for hooks with NAVIGATION_ITEM type
     */
    public static NavigationElement triggerProvidesNavigationItemListener(Class withType, Node node, Navigation navigation) {
        return ProvidesHelper.triggerListener(Provides.Type.NAVIGATION_ITEM, withType, node, Navigation.class, navigation);
    }

    public static void triggerBeforeNavigationItemLoaded(Class withType, Node node, Navigation navigation) {
        OnLoadHelper.triggerBeforeListener(OnLoad.Type.NAVIGATION_ITEM, withType, node, Navigation.class, navigation);
    }

    public static void triggerAfterNavigationItemLoaded(Class withType, Node node, Navigation navigation, NavigationElement navigationElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(Navigation.class, navigation);
        args.put(NavigationElement.class, navigationElement);
        OnLoadHelper.triggerAfterListener(OnLoad.Type.NAVIGATION_ITEM, withType, node, args);
    }

}

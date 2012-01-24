package origo.helpers;

import play.modules.origo.core.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NavigationHelper {

    public static Collection<play.modules.origo.core.ui.NavigationElement> getNavigation(Node node, String section) {
        String navigationType = SettingsHelper.getNavigationType();
        return triggerProvidesNavigationListener(navigationType, node, section);
    }

    /*
     * Convenience methods for hooks with NAVIGATION type
     */
    public static Collection<play.modules.origo.core.ui.NavigationElement> triggerProvidesNavigationListener(String withType, Node node, String section) {
        return ProvidesHelper.triggerListener(play.modules.origo.core.annotations.Provides.Type.NAVIGATION, withType, node, Collections.<Class, Object>singletonMap(String.class, section));
    }

    public static void triggerBeforeNavigationLoaded(String withType, Node node, Collection<play.modules.origo.core.ui.NavigationElement> navigationElements, String section) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        //TODO: Figure out how to do this with a complete type Collection<NavigationElement>.class? instead of Collection.class
        args.put(Collection.class, navigationElements);
        args.put(String.class, section);
        OnLoadHelper.triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type.NAVIGATION, withType, node, args);
    }

    public static void triggerAfterNavigationLoaded(String withType, Node node, Collection<play.modules.origo.core.ui.NavigationElement> navigationElements, String section) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        //TODO: Figure out how to do this with a complete type Collection<NavigationElement>.class? instead of Collection.class
        args.put(Collection.class, navigationElements);
        args.put(String.class, section);
        OnLoadHelper.triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type.NAVIGATION, withType, node, args);
    }

    /*
     * Convenience methods for hooks with NAVIGATION_ITEM type
     */
    public static play.modules.origo.core.ui.NavigationElement triggerProvidesNavigationItemListener(String withType, Node node, play.modules.origo.core.Navigation navigation) {
        return ProvidesHelper.triggerListener(play.modules.origo.core.annotations.Provides.Type.NAVIGATION_ITEM, withType, node, play.modules.origo.core.Navigation.class, navigation);
    }

    public static play.modules.origo.core.ui.NavigationElement triggerProvidesNavigationItemListener(String withType, Node node, play.modules.origo.core.Navigation navigation, play.modules.origo.core.ui.NavigationElement navigationElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(play.modules.origo.core.Navigation.class, navigation);
        args.put(play.modules.origo.core.ui.NavigationElement.class, navigationElement);
        return ProvidesHelper.triggerListener(play.modules.origo.core.annotations.Provides.Type.NAVIGATION_ITEM, withType, node, args);
    }

    public static void triggerBeforeNavigationItemLoaded(String withType, Node node, play.modules.origo.core.Navigation navigation) {
        OnLoadHelper.triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type.NAVIGATION_ITEM, withType, node, play.modules.origo.core.Navigation.class, navigation);
    }

    public static void triggerAfterNavigationItemLoaded(String withType, Node node, play.modules.origo.core.Navigation navigation, play.modules.origo.core.ui.NavigationElement navigationElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(play.modules.origo.core.Navigation.class, navigation);
        args.put(play.modules.origo.core.ui.NavigationElement.class, navigationElement);
        OnLoadHelper.triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type.NAVIGATION_ITEM, withType, node, args);
    }

}

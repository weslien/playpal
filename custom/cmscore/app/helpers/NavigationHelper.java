package helpers;

import models.cmscore.navigation.Navigation;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.OnLoad;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.ui.NavigationElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NavigationHelper {

    public static Collection<NavigationElement> getNavigation(Node node) {
        Collection<NavigationElement> navigationLinks = new ArrayList<NavigationElement>();
        Collection<Navigation> navigationModels = Navigation.findTopWithSection(NavigationElement.FRONT);
        for (Navigation navigationModel : navigationModels) {
            triggerBeforeNavigationItemLoaded(navigationModel.getTypeClass(), node, navigationModel);
            NavigationElement navigationElement = ProvidesHelper.triggerListener(Provides.Type.NAVIGATION_ITEM, navigationModel.getTypeClass(), node, Navigation.class, navigationModel);
            triggerAfterNavigationItemLoaded(navigationModel.getTypeClass(), node, navigationModel, navigationElement);
            navigationLinks.add(navigationElement);
        }
        return navigationLinks;
    }

    /*
     * Convenience methods for hooks with NAVIGATION_ITEM type
     */
    public static Node triggerProvidesNavigationItemListener(Class withType, Node node, Navigation navigation) {
        return ProvidesHelper.triggerListener(Provides.Type.NODE, withType, node, Navigation.class, navigation);
    }

    public static void triggerBeforeNavigationItemLoaded(Class withType, Node node, Navigation navigation) {
        OnLoadHelper.triggerBeforeListener(OnLoad.Type.NODE, withType, node, Navigation.class, navigation);
    }

    public static void triggerAfterNavigationItemLoaded(Class withType, Node node, Navigation navigation, NavigationElement navigationElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(Navigation.class, navigation);
        args.put(NavigationElement.class, navigationElement);
        OnLoadHelper.triggerAfterListener(OnLoad.Type.NAVIGATION_ITEM, withType, node, args);
    }

}

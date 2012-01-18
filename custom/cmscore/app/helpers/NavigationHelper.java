package helpers;

import models.cmscore.navigation.Navigation;
import play.modules.cmscore.Node;
import play.modules.cmscore.ui.NavigationElement;

import java.util.ArrayList;
import java.util.Collection;

public class NavigationHelper {

    public static Collection<NavigationElement> getNavigation(Node node) {
        Collection<NavigationElement> navigationLinks = new ArrayList<NavigationElement>();
        Collection<Navigation> navigationModels = Navigation.findTopWithSection(NavigationElement.FRONT);
        for (Navigation navigationModel : navigationModels) {
            triggerBeforeNavigationLoaded(navigationModel.getTypeClass(), node, navigationModel);
            NavigationElement navigationElement = triggerProvidesNavigationListener(navigationModel.getTypeClass(), node, navigationModel);
            triggerAfterNavigationLoaded(navigationModel.getTypeClass(), node, navigationElement);
            navigationLinks.add(navigationElement);
        }
        return navigationLinks;
    }

    public static NavigationElement triggerProvidesNavigationListener(Class withType, Node node, Navigation navigation) {
        return ProvidesHelper.triggerNavigationListener(withType, node, navigation);
    }

    public static void triggerBeforeNavigationLoaded(Class withType, Node node, Navigation navigation) {
        //TODO: add the NavigationLoadedHelper
        //NavigationLoadedHelper.triggerBeforeListener(node, navigation);
    }

    public static void triggerAfterNavigationLoaded(Class withType, Node node, NavigationElement navigationElement) {
        //TODO: add the NavigationLoadedHelper
        //NavigationLoadedHelper.triggerAfterListener(node, navigation);
    }

}

package helpers;

import models.cmscore.navigation.Navigation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.NavigationElement;

import java.util.ArrayList;
import java.util.Collection;

public class NavigationHelper {

    public static Collection<NavigationElement> getNavigation(Leaf leaf) {
        Collection<NavigationElement> navigationLinks = new ArrayList<NavigationElement>();
        Collection<Navigation> navigationModels = Navigation.findTopWithSection(NavigationElement.FRONT);
        for (Navigation navigationModel : navigationModels) {
            triggerBeforeNavigationLoaded(navigationModel.getTypeClass(), leaf, navigationModel);
            NavigationElement navigationElement = triggerProvidesNavigationListener(navigationModel.getTypeClass(), leaf, navigationModel);
            triggerAfterNavigationLoaded(navigationModel.getTypeClass(), leaf, navigationElement);
            navigationLinks.add(navigationElement);
        }
        return navigationLinks;
    }

    public static NavigationElement triggerProvidesNavigationListener(Class withType, Leaf leaf, Navigation navigation) {
        return ProvidesHelper.triggerNavigationListener(withType, leaf, navigation);
    }

    public static void triggerBeforeNavigationLoaded(Class withType, Leaf leaf, Navigation navigation) {
        //TODO: add the NavigationLoadedHelper
        //NavigationLoadedHelper.triggerBeforeListener(leaf, navigation);
    }

    public static void triggerAfterNavigationLoaded(Class withType, Leaf leaf, NavigationElement navigationElement) {
        //TODO: add the NavigationLoadedHelper
        //NavigationLoadedHelper.triggerAfterListener(leaf, navigation);
    }

}

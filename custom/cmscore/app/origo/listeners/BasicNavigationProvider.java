package origo.listeners;

import models.origo.core.Alias;
import models.origo.core.RootNode;
import models.origo.core.navigation.AliasNavigation;
import models.origo.core.navigation.BasicNavigation;
import models.origo.core.navigation.ExternalLinkNavigation;
import models.origo.core.navigation.PageIdNavigation;
import origo.helpers.NavigationHelper;
import origo.helpers.ProvidesHelper;
import play.modules.origo.core.Navigation;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.NavigationElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BasicNavigationProvider {

    @Provides(type = Provides.TYPE_NAVIGATION, with = "models.origo.core.navigation.BasicNavigation")
    public static Collection<NavigationElement> createNavigation(Node node, String section) {
        Collection<NavigationElement> navigationElements = new ArrayList<NavigationElement>();
        NavigationHelper.triggerBeforeNavigationLoaded(BasicNavigation.class.getName(), node, navigationElements, section);
        List<BasicNavigation> navigationModels = BasicNavigation.findWithSection(section);
        for (BasicNavigation navigationModel : navigationModels) {
            NavigationHelper.triggerBeforeNavigationItemLoaded(navigationModel.type, node, navigationModel);
            NavigationElement navigationElement = NavigationHelper.triggerProvidesNavigationItemListener(navigationModel.type, node, navigationModel);
            NavigationHelper.triggerAfterNavigationItemLoaded(navigationModel.type, node, navigationModel, navigationElement);
            List<NavigationElement> children = createNavigationChildren(node, section, navigationModel, navigationElement);
            navigationElement.children.addAll(children);
            navigationElements.add(navigationElement);
        }
        NavigationHelper.triggerAfterNavigationLoaded(BasicNavigation.class.getName(), node, navigationElements, section);
        return navigationElements;
    }

    public static List<NavigationElement> createNavigationChildren(Node node, String section, BasicNavigation navigationModel, NavigationElement parentNavigationElement) {
        List<NavigationElement> navigationElements = new ArrayList<NavigationElement>();
        List<BasicNavigation> navigationModels = BasicNavigation.findWithSection(section, navigationModel);
        for (BasicNavigation childNavigation : navigationModels) {
            NavigationHelper.triggerBeforeNavigationItemLoaded(childNavigation.type, node, childNavigation);
            NavigationElement childNavigationElement = NavigationHelper.triggerProvidesNavigationItemListener(childNavigation.type, node, childNavigation, parentNavigationElement);
            NavigationHelper.triggerAfterNavigationItemLoaded(childNavigation.type, node, childNavigation, childNavigationElement);
            if (childNavigationElement.selected) {
                parentNavigationElement.selected = true;
            }
            navigationElements.add(childNavigationElement);
        }
        return navigationElements;
    }

    @Provides(type = Provides.TYPE_NAVIGATION_ITEM, with = "models.origo.core.navigation.AliasNavigation")
    public static NavigationElement createAliasNavigation(Node node, Navigation navigation) {
        AliasNavigation navigationModel = AliasNavigation.findWithIdentifier(navigation.getReferenceId());
        Alias alias = Alias.findWithPath(navigationModel.alias);
        if (alias != null) {
            RootNode referencedRootNode = RootNode.findWithUuidLatestPublishedVersion(alias.pageId, new Date());
            if (referencedRootNode != null) {
                Node referencedNode = ProvidesHelper.triggerListener(Provides.TYPE_NODE, referencedRootNode.type, referencedRootNode);
                boolean selected = referencedNode.getNodeId().equals(alias.pageId);
                return new NavigationElement(navigation.getSection(), referencedNode.getTitle(), navigationModel.getLink(), selected);
            } else {
                throw new RuntimeException("Page not found [" + alias.pageId + "]");
            }
        } else {
            throw new RuntimeException("Alias not found [" + navigationModel.alias + "]");
        }
    }

    @Provides(type = Provides.TYPE_NAVIGATION_ITEM, with = "models.origo.core.navigation.PageIdNavigation")
    public static NavigationElement createPageIdNavigation(Node node, Navigation navigation) {
        PageIdNavigation navigationModel = PageIdNavigation.findWithIdentifier(navigation.getReferenceId());
        RootNode referencedRootNode = RootNode.findWithUuidLatestPublishedVersion(navigationModel.pageId, new Date());
        if (referencedRootNode != null) {
            Node referencedNode = ProvidesHelper.triggerListener(Provides.TYPE_NODE, referencedRootNode.type, referencedRootNode);
            boolean selected = referencedRootNode.getNodeId().equals(referencedRootNode.getNodeId());
            return new NavigationElement(navigation.getSection(), referencedNode.getTitle(), navigationModel.getLink(), selected);
        } else {
            throw new RuntimeException("Page not found [" + navigationModel.pageId + "]");
        }
    }

    @Provides(type = Provides.TYPE_NAVIGATION_ITEM, with = "models.origo.core.navigation.ExternalLinkNavigation")
    public static NavigationElement createExternalLinkNavigation(Navigation navigation) {
        ExternalLinkNavigation navigationModel = ExternalLinkNavigation.findWithIdentifier(navigation.getReferenceId());
        return new NavigationElement(navigation.getSection(), navigationModel.title, navigationModel.getLink());
    }

}

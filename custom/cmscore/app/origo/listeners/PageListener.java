package origo.listeners;

import models.origo.core.*;
import models.origo.core.navigation.AliasNavigation;
import models.origo.core.navigation.BasicNavigation;
import models.origo.core.navigation.ExternalLinkNavigation;
import models.origo.core.navigation.PageIdNavigation;
import org.apache.commons.lang.StringUtils;
import origo.helpers.NavigationHelper;
import play.Logger;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.annotations.UIElementType;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.UIElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PageListener {

    @Provides(type = Provides.Type.NODE, with = "models.origo.core.BasicPage")
    public static BasicPage createPage(RootNode rootNode) {

        BasicPage page = BasicPage.findWithUuidSpecificVersion(rootNode.uuid, rootNode.version);
        if (page == null) {
            throw new PageNotFoundException(rootNode.uuid);
        }
        page.rootNode = rootNode;

        return page;
    }

    @OnLoad(type = OnLoad.Type.NODE, with = "models.origo.core.BasicPage")
    public static void createContent(Node node) {

        Collection<Segment> segments = Segment.findWithUuidSpecificVersion(node.getNodeId(), node.getVersion());
        for (Segment segment : segments) {
            UIElement uiElement = createContent(segment);
            if (uiElement != null) {
                node.addUIElement(uiElement);
            }
        }
    }

    public static UIElement createContent(Segment segment) {
        if (!StringUtils.isBlank(segment.referenceId)) {
            if (segment.type.equals(Content.class.getName())) {
                Content content = Content.findWithIdentifier(segment.referenceId);
                if (content != null) {
                    // TODO: Remove segment.weight.intValue when the long/int defect (#521) is fixed
                    return new UIElement(content.identifier, UIElementType.TEXT, content.value);
                }
            } else {
                Logger.warn("Unknown type [" + segment.type + "] for segment [" + segment + "]");
            }
        }
        //TODO: Handle this somehow, in dev/admin maybe show a UIElement with a warning message and in prod swallow error?
        return null;
    }

    @Provides(type = Provides.Type.NAVIGATION, with = "models.origo.core.navigation.BasicNavigation")
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

    public static List<NavigationElement> createNavigationChildren(play.modules.origo.core.Node node, String section, BasicNavigation navigationModel, NavigationElement parentNavigationElement) {
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

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = "models.origo.core.navigation.AliasNavigation")
    public static NavigationElement createAliasNavigation(Node node, play.modules.origo.core.Navigation navigation) {
        AliasNavigation navigationModel = AliasNavigation.findWithIdentifier(navigation.getReferenceId());
        Alias alias = Alias.findWithPath(navigationModel.alias);
        if (alias != null) {
            BasicPage page = BasicPage.findCurrentVersion(alias.pageId, new Date());
            if (page != null) {
                boolean selected = node.getNodeId().equals(alias.pageId);
                return new NavigationElement(navigation.getSection(), page.title, navigationModel.getLink(), selected);
            } else {
                throw new RuntimeException("Page not found [" + alias.pageId + "]");
            }
        } else {
            throw new RuntimeException("Alias not found [" + navigationModel.alias + "]");
        }
    }

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = "models.origo.core.navigation.PageIdNavigation")
    public static NavigationElement createPageIdNavigation(Node node, play.modules.origo.core.Navigation navigation) {
        PageIdNavigation navigationModel = PageIdNavigation.findWithIdentifier(navigation.getReferenceId());
        BasicPage page = BasicPage.findCurrentVersion(navigationModel.pageId, new Date());
        if (page != null) {
            boolean selected = node.getNodeId().equals(page.getNodeId());
            return new NavigationElement(navigation.getSection(), page.title, navigationModel.getLink(), selected);
        } else {
            throw new RuntimeException("Page not found [" + navigationModel.pageId + "]");
        }
    }

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = "models.origo.core.navigation.ExternalLinkNavigation")
    public static NavigationElement createExternalLinkNavigation(play.modules.origo.core.Navigation navigation) {
        ExternalLinkNavigation navigationModel = ExternalLinkNavigation.findWithIdentifier(navigation.getReferenceId());
        return new NavigationElement(navigation.getSection(), navigationModel.title, navigationModel.getLink());
    }

}

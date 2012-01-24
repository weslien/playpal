package origo.listeners;

import models.origo.core.*;
import models.origo.core.navigation.AliasNavigation;
import models.origo.core.navigation.BasicNavigation;
import models.origo.core.navigation.ExternalLinkNavigation;
import models.origo.core.navigation.PageIdNavigation;
import org.apache.commons.lang.StringUtils;
import origo.helpers.NavigationHelper;
import play.modules.origo.core.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PageListener {

    @play.modules.origo.core.annotations.Provides(type = play.modules.origo.core.annotations.Provides.Type.NODE, with = "models.origo.core.Page")
    public static Page createPage(RootNode rootNode) {

        Page page = Page.findWithUuidSpecificVersion(rootNode.uuid, rootNode.version);
        if (page == null) {
            throw new PageNotFoundException(rootNode.uuid);
        }
        page.rootNode = rootNode;

        return page;
    }

    @play.modules.origo.core.annotations.Provides(type = play.modules.origo.core.annotations.Provides.Type.SEGMENT, with = "models.origo.core.Content")
    public static play.modules.origo.core.ui.UIElement createContent(Segment segment) {
        if (!StringUtils.isBlank(segment.referenceId)) {
            Content content = Content.findWithIdentifier(segment.referenceId);
            // TODO: Remove segment.weight.intValue when the long/int defect (#521) is fixed
            return new play.modules.origo.core.ui.UIElement(segment.nodeId, play.modules.origo.core.annotations.UIElementType.TEXT, segment.weight.intValue(), content.value);
        } else {
            //TODO: Handle this somehow, in dev/admin maybe show a message and in prod swallow error?
            return null;
        }
    }

    @play.modules.origo.core.annotations.Provides(type = play.modules.origo.core.annotations.Provides.Type.NAVIGATION, with = "models.origo.core.navigation.BasicNavigation")
    public static Collection<play.modules.origo.core.ui.NavigationElement> createNavigation(Node node, String section) {
        Collection<play.modules.origo.core.ui.NavigationElement> navigationElements = new ArrayList<play.modules.origo.core.ui.NavigationElement>();
        NavigationHelper.triggerBeforeNavigationLoaded(BasicNavigation.class.getName(), node, navigationElements, section);
        List<BasicNavigation> navigationModels = BasicNavigation.findWithSection(section);
        for (BasicNavigation navigationModel : navigationModels) {
            NavigationHelper.triggerBeforeNavigationItemLoaded(navigationModel.type, node, navigationModel);
            play.modules.origo.core.ui.NavigationElement navigationElement = NavigationHelper.triggerProvidesNavigationItemListener(navigationModel.type, node, navigationModel);
            NavigationHelper.triggerAfterNavigationItemLoaded(navigationModel.type, node, navigationModel, navigationElement);
            List<play.modules.origo.core.ui.NavigationElement> children = createNavigationChildren(node, section, navigationModel, navigationElement);
            navigationElement.children.addAll(children);
            navigationElements.add(navigationElement);
        }
        NavigationHelper.triggerAfterNavigationLoaded(BasicNavigation.class.getName(), node, navigationElements, section);
        return navigationElements;
    }

    public static List<play.modules.origo.core.ui.NavigationElement> createNavigationChildren(play.modules.origo.core.Node node, String section, BasicNavigation navigationModel, play.modules.origo.core.ui.NavigationElement parentNavigationElement) {
        List<play.modules.origo.core.ui.NavigationElement> navigationElements = new ArrayList<play.modules.origo.core.ui.NavigationElement>();
        List<BasicNavigation> navigationModels = BasicNavigation.findWithSection(section, navigationModel);
        for (BasicNavigation childNavigation : navigationModels) {
            NavigationHelper.triggerBeforeNavigationItemLoaded(childNavigation.type, node, childNavigation);
            play.modules.origo.core.ui.NavigationElement childNavigationElement = NavigationHelper.triggerProvidesNavigationItemListener(childNavigation.type, node, childNavigation, parentNavigationElement);
            NavigationHelper.triggerAfterNavigationItemLoaded(childNavigation.type, node, childNavigation, childNavigationElement);
            if (childNavigationElement.selected) {
                parentNavigationElement.selected = true;
            }
            navigationElements.add(childNavigationElement);
        }
        return navigationElements;
    }

    @play.modules.origo.core.annotations.Provides(type = play.modules.origo.core.annotations.Provides.Type.NAVIGATION_ITEM, with = "models.origo.core.navigation.AliasNavigation")
    public static play.modules.origo.core.ui.NavigationElement createAliasNavigation(Node node, play.modules.origo.core.Navigation navigation) {
        AliasNavigation navigationModel = AliasNavigation.findWithIdentifier(navigation.getReferenceId());
        Alias alias = Alias.findWithPath(navigationModel.alias);
        if (alias != null) {
            Page page = Page.findCurrentVersion(alias.pageId, new Date());
            if (page != null) {
                boolean selected = node.getNodeId().equals(alias.pageId);
                return new play.modules.origo.core.ui.NavigationElement(navigation.getSection(), page.title, navigationModel.getLink(), selected);
            } else {
                throw new RuntimeException("Page not found [" + alias.pageId + "]");
            }
        } else {
            throw new RuntimeException("Alias not found [" + navigationModel.alias + "]");
        }
    }

    @play.modules.origo.core.annotations.Provides(type = play.modules.origo.core.annotations.Provides.Type.NAVIGATION_ITEM, with = "models.origo.core.navigation.PageIdNavigation")
    public static play.modules.origo.core.ui.NavigationElement createPageIdNavigation(Node node, play.modules.origo.core.Navigation navigation) {
        PageIdNavigation navigationModel = PageIdNavigation.findWithIdentifier(navigation.getReferenceId());
        Page page = Page.findCurrentVersion(navigationModel.pageId, new Date());
        if (page != null) {
            boolean selected = node.getNodeId().equals(page.getNodeId());
            return new play.modules.origo.core.ui.NavigationElement(navigation.getSection(), page.title, navigationModel.getLink(), selected);
        } else {
            throw new RuntimeException("Page not found [" + navigationModel.pageId + "]");
        }
    }

    @play.modules.origo.core.annotations.Provides(type = play.modules.origo.core.annotations.Provides.Type.NAVIGATION_ITEM, with = "models.origo.core.navigation.ExternalLinkNavigation")
    public static play.modules.origo.core.ui.NavigationElement createExternalLinkNavigation(play.modules.origo.core.Navigation navigation) {
        ExternalLinkNavigation navigationModel = ExternalLinkNavigation.findWithIdentifier(navigation.getReferenceId());
        return new play.modules.origo.core.ui.NavigationElement(navigation.getSection(), navigationModel.title, navigationModel.getLink());
    }

}

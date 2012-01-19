package listeners;

import helpers.NavigationHelper;
import models.cmscore.*;
import models.cmscore.navigation.AliasNavigation;
import models.cmscore.navigation.BasicNavigation;
import models.cmscore.navigation.ExternalLinkNavigation;
import models.cmscore.navigation.PageIdNavigation;
import org.apache.commons.lang.StringUtils;
import play.modules.cmscore.Navigation;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.UIElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PageListener {

    @Provides(type = Provides.Type.NODE, with = Page.class)
    public static Page createPage(RootNode rootNode) {

        Page page = Page.findWithUuidSpecificVersion(rootNode.uuid, rootNode.version);
        if (page == null) {
            throw new PageNotFoundException(rootNode.uuid);
        }
        page.rootNode = rootNode;

        return page;
    }

    @Provides(type = Provides.Type.SEGMENT, with = Content.class)
    public static UIElement createContent(Segment segment) {
        if (!StringUtils.isBlank(segment.referenceId)) {
            Content content = Content.findWithIdentifier(segment.referenceId);
            // TODO: Remove segment.weight.intValue when the long/int defect (#521) is fixed
            return new UIElement(segment.nodeId, UIElementType.TEXT, segment.weight.intValue(), content.value);
        } else {
            //TODO: Handle this somehow, in dev/admin maybe show a message and in prod swallow error?
            return null;
        }
    }

    @Provides(type = Provides.Type.NAVIGATION, with = BasicNavigation.class)
    public static Collection<NavigationElement> createNavigation(Node node, String section) {
        Collection<NavigationElement> navigationElements = new ArrayList<NavigationElement>();
        NavigationHelper.triggerBeforeNavigationLoaded(BasicNavigation.class, node, navigationElements, section);
        Collection<BasicNavigation> navigationModels = BasicNavigation.findTopWithSection(section);
        for (BasicNavigation navigationModel : navigationModels) {
            NavigationHelper.triggerBeforeNavigationItemLoaded(navigationModel.getTypeClass(), node, navigationModel);
            NavigationElement navigationElement = NavigationHelper.triggerProvidesNavigationItemListener(navigationModel.getTypeClass(), node, navigationModel);
            NavigationHelper.triggerAfterNavigationItemLoaded(navigationModel.getTypeClass(), node, navigationModel, navigationElement);
            navigationElements.add(navigationElement);
        }
        NavigationHelper.triggerAfterNavigationLoaded(BasicNavigation.class, node, navigationElements, section);
        return navigationElements;
    }

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = AliasNavigation.class)
    public static NavigationElement createAliasNavigation(Navigation navigation) {
        AliasNavigation navigationModel = AliasNavigation.findWithIdentifier(navigation.getReferenceId());
        Alias alias = Alias.findWithPath(navigationModel.alias);
        if (alias != null) {
            Page page = Page.findCurrentVersion(alias.pageId, new Date());
            if (page != null) {
                return new NavigationElement(navigation.getSection(), page.title, navigationModel.getLink());
            } else {
                throw new RuntimeException("Page not found [" + alias.pageId + "]");
            }
        } else {
            throw new RuntimeException("Alias not found [" + navigationModel.alias + "]");
        }
    }

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = PageIdNavigation.class)
    public static NavigationElement createPageIdNavigation(Navigation navigation) {
        PageIdNavigation navigationModel = PageIdNavigation.findWithIdentifier(navigation.getReferenceId());
        Page page = Page.findCurrentVersion(navigationModel.pageId, new Date());
        if (page != null) {
            return new NavigationElement(navigation.getSection(), page.title, navigationModel.getLink());
        } else {
            throw new RuntimeException("Page not found [" + navigationModel.pageId + "]");
        }
    }

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = ExternalLinkNavigation.class)
    public static NavigationElement createExternalLinkNavigation(Navigation navigation) {
        ExternalLinkNavigation navigationModel = ExternalLinkNavigation.findWithIdentifier(navigation.getReferenceId());
        return new NavigationElement(navigation.getSection(), navigationModel.title, navigationModel.getLink());
    }

}

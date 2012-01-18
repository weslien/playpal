package listeners;

import models.cmscore.*;
import models.cmscore.navigation.AliasNavigation;
import models.cmscore.navigation.ExternalLinkNavigation;
import models.cmscore.navigation.Navigation;
import models.cmscore.navigation.PageIdNavigation;
import org.apache.commons.lang.StringUtils;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.OnLoad;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.UIElement;

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

    /**
     * TODO: Just for testing, remove eventually
     *
     * @param node the node to modify
     */
    @OnLoad(type = OnLoad.Type.NODE, with = Page.class)
    public static void setupTestData(Node node) {

        UIElement metaElement = new UIElement(UIElementType.META, 10);
        metaElement.getAttributes().put("http-equiv", "Content-Type");
        metaElement.getAttributes().put("content", "text/html; charset=utf-8");
        node.addUIElement(metaElement);

        UIElement styleElementWithContent = new UIElement(UIElementType.STYLE, 10, "\n\tbody {\n\t\tbackground-color: #ff0000;\n}");
        styleElementWithContent.put("type", "text/css");
        node.addUIElement(styleElementWithContent);

        UIElement styleElementWithSrc = new UIElement(UIElementType.STYLE, 10);
        styleElementWithSrc.put("type", "text/css");
        styleElementWithSrc.put("src", "test/css/style_head.css");
        node.addUIElement("head", styleElementWithSrc);

        UIElement inlineStyleElement = new UIElement(UIElementType.STYLE, 10);
        inlineStyleElement.put("type", "text/css");
        inlineStyleElement.put("src", "test/css/style_inline.css");
        node.addUIElement("main", inlineStyleElement);

        UIElement panel = new UIElement(UIElementType.PANEL, 10);
        node.addUIElement("left", panel);

        panel.addChild(new UIElement(UIElementType.TEXT, 12, "Bla bla"));

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

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = AliasNavigation.class)
    public static NavigationElement createAliasNavigation(Navigation navigation) {
        AliasNavigation navigationModel = AliasNavigation.findWithIdentifier(navigation.referenceId);
        Alias alias = Alias.findWithPath(navigationModel.alias);
        if (alias != null) {
            Page page = Page.findCurrentVersion(alias.pageId, new Date());
            if (page != null) {
                return new NavigationElement(navigation.section, page.title, navigationModel.getLink());
            } else {
                throw new RuntimeException("Page not found [" + alias.pageId + "]");
            }
        } else {
            throw new RuntimeException("Alias not found [" + navigationModel.alias + "]");
        }
    }

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = PageIdNavigation.class)
    public static NavigationElement createPageIdNavigation(Navigation navigation) {
        PageIdNavigation navigationModel = PageIdNavigation.findWithIdentifier(navigation.referenceId);
        Page page = Page.findCurrentVersion(navigationModel.pageId, new Date());
        if (page != null) {
            return new NavigationElement(navigation.section, page.title, navigationModel.getLink());
        } else {
            throw new RuntimeException("Page not found [" + navigationModel.pageId + "]");
        }
    }

    @Provides(type = Provides.Type.NAVIGATION_ITEM, with = ExternalLinkNavigation.class)
    public static NavigationElement createExternalLinkNavigation(Navigation navigation) {
        ExternalLinkNavigation navigationModel = ExternalLinkNavigation.findWithIdentifier(navigation.referenceId);
        return new NavigationElement(navigation.section, navigationModel.title, navigationModel.getLink());
    }

}

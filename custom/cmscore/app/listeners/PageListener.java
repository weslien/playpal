package listeners;

import models.cmscore.*;
import models.cmscore.navigation.AliasNavigation;
import models.cmscore.navigation.ExternalLinkNavigation;
import models.cmscore.navigation.Navigation;
import models.cmscore.navigation.PageIdNavigation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.annotations.LeafLoaded;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.annotations.ProvidesType;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.UIElement;

import java.util.Date;

public class PageListener {

    @Provides(type = ProvidesType.LEAF, with = Page.class)
    public static Page createPage(RootLeaf rootLeaf) {

        Page page = Page.findWithUuidSpecificVersion(rootLeaf.uuid, rootLeaf.version);
        if (page == null) {
            throw new PageNotFoundException(rootLeaf.uuid);
        }
        page.rootLeaf = rootLeaf;

        return page;
    }

    /**
     * TODO: Just for testing, remove eventually
     *
     * @param leaf the leaf to modify
     */
    @LeafLoaded(type = Page.class)
    public static void setupTestData(Leaf leaf) {

        UIElement metaElement = new UIElement(UIElementType.META, 10);
        metaElement.getAttributes().put("http-equiv", "Content-Type");
        metaElement.getAttributes().put("content", "text/html; charset=utf-8");
        leaf.addUIElement(metaElement);

        UIElement styleElementWithContent = new UIElement(UIElementType.STYLE, 10, "\n\tbody {\n\t\tbackground-color: #ff0000;\n}");
        styleElementWithContent.put("type", "text/css");
        leaf.addUIElement(styleElementWithContent);

        UIElement styleElementWithSrc = new UIElement(UIElementType.STYLE, 10);
        styleElementWithSrc.put("type", "text/css");
        styleElementWithSrc.put("src", "test/css/style_head.css");
        leaf.addUIElement("head", styleElementWithSrc);

        UIElement inlineStyleElement = new UIElement(UIElementType.STYLE, 10);
        inlineStyleElement.put("type", "text/css");
        inlineStyleElement.put("src", "test/css/style_inline.css");
        leaf.addUIElement("main", inlineStyleElement);

        UIElement panel = new UIElement(UIElementType.PANEL, 10);
        leaf.addUIElement("left", panel);

        panel.addChild(new UIElement(UIElementType.TEXT, 12, "Bla bla"));

    }

    @Provides(type = ProvidesType.BLOCK, with = Content.class)
    public static UIElement createContent(Block block) {
        Content content = Content.findWithIdentifier(block.referenceId);
        // TODO: Remove block.weight.intValue when the long/int defect (#521) is fixed
        return new UIElement(block.leafId, UIElementType.TEXT, block.weight.intValue(), content.value);
    }

    @Provides(type = ProvidesType.NAVIGATION, with = AliasNavigation.class)
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

    @Provides(type = ProvidesType.NAVIGATION, with = PageIdNavigation.class)
    public static NavigationElement createPageIdNavigation(Navigation navigation) {
        PageIdNavigation navigationModel = PageIdNavigation.findWithIdentifier(navigation.referenceId);
        Page page = Page.findCurrentVersion(navigationModel.pageId, new Date());
        if (page != null) {
            return new NavigationElement(navigation.section, page.title, navigationModel.getLink());
        } else {
            throw new RuntimeException("Page not found [" + navigationModel.pageId + "]");
        }
    }

    @Provides(type = ProvidesType.NAVIGATION, with = ExternalLinkNavigation.class)
    public static NavigationElement createExternalLinkNavigation(Navigation navigation) {
        ExternalLinkNavigation navigationModel = ExternalLinkNavigation.findWithIdentifier(navigation.referenceId);
        return new NavigationElement(navigation.section, navigationModel.title, navigationModel.getLink());
    }

}

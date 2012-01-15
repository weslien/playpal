package listeners;

import models.cmscore.Block;
import models.cmscore.Content;
import models.cmscore.Page;
import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.annotations.LeafLoaded;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.annotations.ProvidesType;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.UIElement;

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
     * @param leaf
     */
    @LeafLoaded(type = Page.class)
    public static void buildPageContent(Leaf leaf) {

        UIElement metaElement = new UIElement(UIElementType.META, 10);
        metaElement.getAttributes().put("http-equiv", "Content-Type");
        metaElement.getAttributes().put("content", "text/html; charset=utf-8");
        leaf.addUIElement(metaElement);

        UIElement styleElementWithContent = new UIElement(UIElementType.STYLE, 10, "\n\tbody {\n\t\tbackground-color: #ff0000;}");
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
        Content content = Content.findByIdentifier(block.referenceId);
        // TODO: Remove block.weight.intValue when the long/int defect (#521) is fixed
        return new UIElement(block.leafId, UIElementType.TEXT, block.weight.intValue(), content.value);
    }

}

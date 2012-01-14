package listeners;

import models.cmscore.Block;
import models.cmscore.Content;
import models.cmscore.Page;
import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Renderable;
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
    
    @LeafLoaded(type = Page.class)
    public static void buildPageContent(Leaf leaf) {

        UIElement metaElement = new UIElement(UIElementType.META, 10);
        metaElement.getAttributes().put("http-equiv", "Content-Type");
        metaElement.getAttributes().put("content", "text/html; charset=utf-8");
        leaf.addUIElement("head", metaElement);

        UIElement styleElementWithContent = new UIElement(UIElementType.STYLE, 10, "\n\tbody {\n\t\tbackground-color: #ff0000;}");
        styleElementWithContent.put("type", "text/css");
        leaf.addUIElement("head", styleElementWithContent);
        
        UIElement styleElementWithSrc = new UIElement(UIElementType.STYLE, 10);
        styleElementWithSrc.put("type", "text/css");
        styleElementWithSrc.put("src", "test/css/style.css");
        leaf.addUIElement("head", styleElementWithSrc);

        UIElement panel = new UIElement(UIElementType.PANEL, 10);
        leaf.addUIElement("left", panel);

        panel.addChild(new UIElement(UIElementType.TEXT, 12, "Bla bla"));

    }
    
    @Provides(type = ProvidesType.BLOCK, with = Content.class)
    public static Renderable createContent(Block block) {
        return Content.findByIdentifier(block.referenceId);
    }

}

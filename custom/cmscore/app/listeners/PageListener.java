package listeners;

import models.cmscore.Block;
import models.cmscore.Page;
import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.annotations.AfterLeafLoaded;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.UIElement;

import java.util.Collection;

public class PageListener {

    @Provides(type = Page.class)
    public static Page createPage(RootLeaf rootLeaf) {

        Page page = Page.findWithUuidSpecificVersion(rootLeaf.uuid, rootLeaf.version);
        if (page == null) {
            throw new PageNotFoundException(rootLeaf.uuid);
        }
        page.rootLeaf = rootLeaf;

        return page;
    }
    
    @AfterLeafLoaded(type = Page.class)
    public static void buildPageContent(Leaf leaf) {

        UIElement metaElement = new UIElement(UIElementType.META, 10);
        metaElement.getAttributes().put("http-equiv", "Content-Type");
        metaElement.getAttributes().put("content", "text/html; charset=utf-8");
        leaf.addUIElement("head", metaElement);
        
        Collection<Block> blocks = Block.findWithUuidSpecificVersion(leaf.getLeafId(), leaf.getLeafVersion());
        for (Block block : blocks) {
            // TODO: Remove block.weight.intValue when the long/int defect (#521) is fixed
            leaf.addUIElement(block.region, new UIElement(block.leafId, UIElementType.TEXT, block.weight.intValue(), block.content.value));
        }

    }

}

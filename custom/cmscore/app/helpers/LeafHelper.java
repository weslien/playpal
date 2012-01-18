package helpers;

import listeners.PageNotFoundException;
import models.cmscore.Block;
import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.UIElement;

import java.util.Collection;
import java.util.Date;

public class LeafHelper {

    public static Leaf load(String uuid) {
        //Load RootLeaf model
        RootLeaf rootLeaf = RootLeaf.findWithUuidLatestPublishedVersion(uuid, new Date());
        if (rootLeaf == null) {
            throw new PageNotFoundException(uuid);
        }

        return load(rootLeaf);
    }

    public static Leaf load(String uuid, long version) {
        //Load RootLeaf model
        RootLeaf rootLeaf = RootLeaf.findWithUuidSpecificVersion(uuid, version);
        if (rootLeaf == null) {
            throw new PageNotFoundException(uuid);
        }

        return load(rootLeaf);
    }

    private static Leaf load(RootLeaf rootLeaf) {
        boolean hasType = rootLeaf.type != null && !rootLeaf.getTypeClass().equals(RootLeaf.class);
        if (hasType) {
            triggerBeforeLeafLoaded(rootLeaf.getTypeClass(), rootLeaf);
        }

        Leaf leaf = rootLeaf;
        if (hasType) {
            leaf = triggerProvidesLeafListener(rootLeaf.getTypeClass(), rootLeaf);
        }

        if (hasType) {
            triggerAfterLeafLoaded(rootLeaf.getTypeClass(), leaf);
        }

        addBlocks(leaf);

        return leaf;
    }

    private static void addBlocks(Leaf leaf) {
        Collection<Block> blocks = Block.findWithUuidSpecificVersion(leaf.getLeafId(), leaf.getLeafVersion());
        for (Block block : blocks) {
            triggerBeforeBlockLoaded(block.getTypeClass(), leaf, block);
            UIElement uiElement = triggerProvidesBlockListener(block.getTypeClass(), leaf, block);
            if (uiElement != null) {
                triggerAfterBlockLoaded(block.getTypeClass(), leaf, block, uiElement);
                leaf.addUIElement(block.region, uiElement);
            }
        }
    }

    private static void triggerBeforeBlockLoaded(Class blockType, Leaf leaf, Block block) {
        BlockLoadedHelper.triggerBeforeListener(blockType, leaf, block);
    }

    private static void triggerAfterBlockLoaded(Class blockType, Leaf leaf, Block block, UIElement uiElement) {
        BlockLoadedHelper.triggerAfterListener(blockType, leaf, block, uiElement);
    }

    public static Leaf triggerProvidesLeafListener(Class withType, RootLeaf rootLeaf) {
        return ProvidesHelper.triggerLeafListener(withType, rootLeaf);
    }

    public static UIElement triggerProvidesBlockListener(Class withType, Leaf leaf, Block block) {
        return ProvidesHelper.triggerBlockListener(withType, leaf, block);
    }

    public static void triggerBeforeLeafLoaded(Class withType, RootLeaf rootLeaf) {
        LeafLoadedHelper.triggerBeforeListener(withType, rootLeaf);
    }

    public static void triggerAfterLeafLoaded(Class withType, Leaf leaf) {
        LeafLoadedHelper.triggerAfterListener(withType, leaf);
    }

    public static void triggerProvidesFormListener(Class withType, Leaf leaf) {
        ProvidesHelper.triggerFormListener(withType, leaf);
    }
}

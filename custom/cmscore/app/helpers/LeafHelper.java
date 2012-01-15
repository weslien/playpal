package helpers;

import listeners.PageNotFoundException;
import models.cmscore.Block;
import models.cmscore.RootLeaf;
import models.cmscore.navigation.Navigation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.NavigationElement;
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

        addNavigation(leaf);
        addBlocks(leaf);

        return leaf;
    }

    private static void addBlocks(Leaf leaf) {
        Collection<Block> blocks = Block.findWithUuidSpecificVersion(leaf.getLeafId(), leaf.getLeafVersion());
        for (Block block : blocks) {
            triggerBeforeBlockLoaded(block.getTypeClass(), leaf, block);
            UIElement uiElement = triggerProvidesBlockListener(block.getTypeClass(), leaf, block);
            triggerAfterBlockLoaded(block.getTypeClass(), leaf, block, uiElement);
            leaf.addUIElement(block.region, uiElement);
        }
    }

    private static void addNavigation(Leaf leaf) {
        loadNavigation(leaf, null);
    }

    private static void loadNavigation(Leaf leaf, Navigation parent) {
        Collection<Navigation> navigationModels = Navigation.findTopWithSection(NavigationElement.FRONT, parent);
        for (Navigation navigationModel : navigationModels) {
            triggerBeforeNavigationLoaded(navigationModel.getTypeClass(), leaf, navigationModel);
            NavigationElement navigationElement = triggerProvidesNavigationListener(navigationModel.getTypeClass(), leaf, navigationModel);
            triggerAfterNavigationLoaded(navigationModel.getTypeClass(), leaf, navigationElement);
            leaf.addNavigation(navigationElement);
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

    public static NavigationElement triggerProvidesNavigationListener(Class withType, Leaf leaf, Navigation navigation) {
        return ProvidesHelper.triggerNavigationListener(withType, leaf, navigation);
    }

    public static void triggerBeforeLeafLoaded(Class withType, RootLeaf rootLeaf) {
        LeafLoadedHelper.triggerBeforeListener(withType, rootLeaf);
    }

    public static void triggerAfterLeafLoaded(Class withType, Leaf leaf) {
        LeafLoadedHelper.triggerAfterListener(withType, leaf);
    }

    public static void triggerBeforeNavigationLoaded(Class withType, Leaf leaf, Navigation navigation) {
        //TODO: add the NavigationLoadedHelper
        //NavigationLoadedHelper.triggerBeforeListener(leaf, navigation);
    }

    public static void triggerAfterNavigationLoaded(Class withType, Leaf leaf, NavigationElement navigationElement) {
        //TODO: add the NavigationLoadedHelper
        //NavigationLoadedHelper.triggerAfterListener(leaf, navigation);
    }

    public static void triggerProvidesFormListener(Class withType, Leaf leaf) {
        ProvidesHelper.triggerFormListener(withType, leaf);
    }
}

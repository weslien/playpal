package helpers;

import listeners.PageNotFoundException;
import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.annotations.LeafLoaded;

import java.util.Date;

public class LeafHelper {

    public static Leaf load(String uuid) {
        //Load leafModel
        RootLeaf rootLeaf = RootLeaf.findWithUuidLatestPublishedVersion(uuid, new Date());
        if (rootLeaf == null) {
            throw new PageNotFoundException(uuid);
        }

        rootLeaf.init();

        boolean hasType = rootLeaf.type != null && !rootLeaf.getTypeClass().equals(RootLeaf.class);
        if (hasType) {
            triggerBeforeLeafLoaded(rootLeaf.getTypeClass(), rootLeaf);
        }

        Leaf leaf = rootLeaf;
        if (hasType) {
            leaf = triggerProvidesListener(rootLeaf.getTypeClass(), rootLeaf);
        }

        if (hasType) {
            triggerAfterLeafLoaded(rootLeaf.getTypeClass(), leaf);
        }

        return leaf;
    }

    public static Leaf triggerProvidesListener(Class type, RootLeaf rootRootLeaf) {
        return ProvidesHelper.triggerListener(type, rootRootLeaf);
    }

    public static void triggerAfterLeafLoaded(Class type, Leaf leaf) {
        LeafLoadedHelper.triggerListener(type, leaf, LeafLoaded.Order.AFTER);
    }

    public static void triggerBeforeLeafLoaded(Class type, RootLeaf rootLeaf) {
        LeafLoadedHelper.triggerListener(type, rootLeaf, LeafLoaded.Order.BEFORE);
    }

}

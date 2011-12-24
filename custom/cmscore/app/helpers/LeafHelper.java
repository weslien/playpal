package helpers;


import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.annotations.LeafLoaded;

import java.util.Date;

public class LeafHelper {

    public static Leaf load(String uuid) {
        //Load leafModel
        RootLeaf rootRootLeaf = RootLeaf.findWithUuidLatestPublishedVersion(uuid, new Date());

        boolean hasType = rootRootLeaf.type != null && rootRootLeaf.type != RootLeaf.class;
        if (hasType) {
            triggerBeforeLeafLoaded(rootRootLeaf.type, rootRootLeaf);
        }

        Leaf leaf = rootRootLeaf;
        if (hasType) {
            leaf = triggerProvidesListener(rootRootLeaf.type, rootRootLeaf);
        }

        if (hasType) {
            triggerAfterLeafLoaded(rootRootLeaf.type, leaf);
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

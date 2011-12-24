package helpers;


import models.cmscore.Leaf;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.annotations.LeafLoaded;

import java.util.Date;

public class LeafHelper {

    public static LeafType load(String uuid) {
        //Load leafModel
        Leaf rootLeaf = Leaf.findWithUuidLatestPublishedVersion(uuid, new Date());

        boolean hasType = rootLeaf.type != null && rootLeaf.type != Leaf.class;
        if (hasType) {
            triggerBeforeLeafLoaded(rootLeaf.type, rootLeaf);
        }

        LeafType leaf = rootLeaf;
        if (hasType) {
            leaf = triggerProvidesListener(rootLeaf.type, rootLeaf);
        }

        if (hasType) {
            triggerAfterLeafLoaded(rootLeaf.type, leaf);
        }

        return leaf;
    }

    public static LeafType triggerProvidesListener(Class type, Leaf rootLeaf) {
        return ProvidesHelper.triggerListener(type, rootLeaf);
    }

    public static void triggerAfterLeafLoaded(Class type, LeafType leaf) {
        LeafLoadedHelper.triggerListener(type, leaf, LeafLoaded.Order.AFTER);
    }

    public static void triggerBeforeLeafLoaded(Class type, Leaf leaf) {
        LeafLoadedHelper.triggerListener(type, leaf, LeafLoaded.Order.BEFORE);
    }

}

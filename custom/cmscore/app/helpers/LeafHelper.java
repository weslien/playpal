package helpers;


import models.cmscore.Leaf;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.annotations.LeafLoaded;

public class LeafHelper {

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

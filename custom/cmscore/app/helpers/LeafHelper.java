package helpers;

import listeners.PageNotFoundException;
import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;

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
        LeafLoadedHelper.triggerAfterListener(type, leaf);
    }

    public static void triggerBeforeLeafLoaded(Class type, RootLeaf rootLeaf) {
        LeafLoadedHelper.triggerBeforeListener(type, rootLeaf);
    }

    public static void triggerFormProvider(Leaf leaf) {
        FormProviderHelper.triggerListener(leaf);
    }


}

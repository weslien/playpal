package play.modules.cmscore.helpers;

import models.cmscore.Leaf;
import play.modules.cmscore.AfterLeafLoaded;
import play.modules.cmscore.CachedAnnotationListener;
import play.modules.cmscore.Listeners;

import java.util.List;

public class AfterLeafLoadedHelper {

    public static void triggerListener(Class type, Leaf rootLeaf) {
        CachedAnnotationListener listener = findListenerForType(type);
        LeafHelper.invokeListener(rootLeaf, listener);
    }

    private static CachedAnnotationListener findListenerForType(Class type) {
        List<CachedAnnotationListener> listeners = Listeners.getListenersForAnnotationType(AfterLeafLoaded.class);
        for (CachedAnnotationListener listener : listeners) {
            Class annotationType = ((AfterLeafLoaded) listener.annotation).type();
            if (annotationType.equals(type) || annotationType.equals(Object.class)) {
                return listener;
            }
        }
        return null;
    }

}

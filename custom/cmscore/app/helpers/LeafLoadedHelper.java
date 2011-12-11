package helpers;

import models.cmscore.Leaf;
import play.modules.cmscore.CachedAnnotationListener;
import play.modules.cmscore.LeafLoaded;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.SimpleOrder;

import java.util.List;

public class LeafLoadedHelper {

    public static void triggerListener(Class type, Leaf rootLeaf, SimpleOrder order) {
        CachedAnnotationListener listener = findListenerForType(type, order);
        if (listener != null) {
            LeafHelper.invokeListener(rootLeaf, listener);
        }
    }

    private static CachedAnnotationListener findListenerForType(Class type, SimpleOrder order) {
        List<CachedAnnotationListener> listeners = Listeners.getListenersForAnnotationType(LeafLoaded.class);
        for (CachedAnnotationListener listener : listeners) {
            Class annotationType = ((LeafLoaded) listener.annotation).type();
            if (annotationType.equals(type) || annotationType.equals(Object.class)) {
                SimpleOrder annotationOrder = ((LeafLoaded) listener.annotation).order();
                if (annotationOrder == order) {
                    return listener;
                }
            }
        }
        return null;
    }

}

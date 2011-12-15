package helpers;

import play.modules.cmscore.CachedAnnotationListener;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.LeafLoaded;

import java.util.List;

public class LeafLoadedHelper {

    public static void triggerListener(Class type, LeafType rootLeaf, LeafLoaded.Order order) {
        CachedAnnotationListener listener = findListenerForType(type, order);
        if (listener != null) {
            ReflectionHelper.invokeListener(listener, rootLeaf);
        }
    }

    private static CachedAnnotationListener findListenerForType(Class type, LeafLoaded.Order order) {
        List<CachedAnnotationListener> listeners = Listeners.getListenersForAnnotationType(play.modules.cmscore.annotations.LeafLoaded.class);
        for (CachedAnnotationListener listener : listeners) {
            Class annotationType = ((play.modules.cmscore.annotations.LeafLoaded) listener.annotation).type();
            if (annotationType.equals(type) || annotationType.equals(Object.class)) {
                LeafLoaded.Order annotationOrder = ((play.modules.cmscore.annotations.LeafLoaded) listener.annotation).order();
                if (annotationOrder == order) {
                    return listener;
                }
            }
        }
        return null;
    }

}

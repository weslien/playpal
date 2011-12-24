package helpers;

import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.LeafLoaded;

import java.util.List;

/**
 * Helper to trigger \@LeafLoaded listeners. Should not be used directly, use LeafHelper instead.
 * @see LeafHelper
 * @see LeafLoaded
 */
public class LeafLoadedHelper {

    public static void triggerListener(Class type, LeafType rootLeaf, LeafLoaded.Order order) {
        CachedAnnotation listener = findListenerForType(type, order);
        if (listener != null) {
            ReflectionHelper.invokeMethod(listener, rootLeaf);
        }
    }

    private static CachedAnnotation findListenerForType(Class type, LeafLoaded.Order order) {
        List<CachedAnnotation> listeners = Listeners.getListenersForAnnotationType(LeafLoaded.class);
        for (CachedAnnotation listener : listeners) {
            Class annotationType = ((LeafLoaded) listener.annotation).type();
            if (annotationType.equals(type) || annotationType.equals(Object.class)) {
                LeafLoaded.Order annotationOrder = ((LeafLoaded) listener.annotation).order();
                if (annotationOrder == order) {
                    return listener;
                }
            }
        }
        return null;
    }

}

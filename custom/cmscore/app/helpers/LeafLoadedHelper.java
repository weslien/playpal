package helpers;

import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.LeafLoaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@LeafLoaded listeners. Should not be used directly, use LeafHelper instead.
 * @see LeafHelper
 * @see LeafLoaded
 */
public class LeafLoadedHelper {

    public static void triggerListener(Class type, Leaf leaf, LeafLoaded.Order order) {
        CachedAnnotation listener = findListenerForType(type, order);
        if (listener != null) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Leaf.class, leaf);
            ReflectionHelper.invokeMethod(listener.method, parameters);
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

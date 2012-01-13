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
 * @see play.modules.cmscore.annotations.LeafLoaded
 */
public class LeafLoadedHelper {

    public static void triggerBeforeListener(Class type, Leaf leaf) {
        List<CachedAnnotation> listeners = findListenerForType(type, false);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Leaf.class, leaf);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    public static void triggerAfterListener(Class type, Leaf leaf) {
        List<CachedAnnotation> listeners = findListenerForType(type, true);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Leaf.class, leaf);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final Class type, final boolean after) {
        return Listeners.getListenersForAnnotationType(LeafLoaded.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                LeafLoaded annotation = ((LeafLoaded) listener.annotation);
                return (annotation.type().equals(type) || annotation.type().equals(Object.class)) && annotation.after() == after;
            }
        });
    }

}

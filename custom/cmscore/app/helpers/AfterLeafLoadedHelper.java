package helpers;

import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.AfterLeafLoaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@AfterLeafLoaded listeners. Should not be used directly, use LeafHelper instead.
 * @see LeafHelper
 * @see play.modules.cmscore.annotations.AfterLeafLoaded
 */
public class AfterLeafLoadedHelper {

    public static void triggerListener(Class type, Leaf leaf) {
        List<CachedAnnotation> listeners = findListenerForType(type);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Leaf.class, leaf);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final Class type) {
        return Listeners.getListenersForAnnotationType(AfterLeafLoaded.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                Class annotationType = ((AfterLeafLoaded) listener.annotation).type();
                return annotationType.equals(type) || annotationType.equals(Object.class);
            }
        });
    }

}

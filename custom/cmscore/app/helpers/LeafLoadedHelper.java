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

    public static void triggerListener(Class type, LeafType rootLeaf) {
        CachedAnnotation listener = findListenerForType(type);
        if (listener != null) {
            ReflectionHelper.invokeListener(listener, rootLeaf);
        }
    }

    private static CachedAnnotation findListenerForType(Class type) {
        List<CachedAnnotation> listeners = Listeners.getListenersForAnnotationType(play.modules.cmscore.annotations.LeafLoaded.class);
        for (CachedAnnotation listener : listeners) {
            Class annotationType = ((play.modules.cmscore.annotations.LeafLoaded) listener.annotation).type();
            if (annotationType.equals(type) || annotationType.equals(Object.class)) {
                return listener;
            }
        }
        return null;
    }

}

package helpers;

import models.cmscore.RootLeaf;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.Provides;

import java.util.List;

/**
 * Helper to trigger \@Provides listeners. Should not be used directly, use LeafHelper instead.
 * @see LeafHelper
 * @see Provides
 */
public class ProvidesHelper {

    public static Leaf triggerListener(Class type, RootLeaf rootRootLeaf) {

        CachedAnnotation listener = findListenerForType(type);
        if (listener != null) {
            return (Leaf) ReflectionHelper.invokeMethod(listener.method, rootRootLeaf);
        }
        throw new RuntimeException("Every type must have a class annotated with @Provides to instantiate an instance");
    }
    
    private static CachedAnnotation findListenerForType(Class type) {
        List<CachedAnnotation> listeners = Listeners.getListenersForAnnotationType(Provides.class);
        for (CachedAnnotation listener : listeners) {
            if (((Provides)listener.annotation).type().equals(type)) {
                return listener;
            }
        }
        return null;
    }

}

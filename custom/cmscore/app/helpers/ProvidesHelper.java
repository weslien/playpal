package helpers;

import models.cmscore.Leaf;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.Provides;

import java.util.List;

public class ProvidesHelper {

    public static LeafType triggerListener(Class type, Leaf rootLeaf) {

        CachedAnnotation listener = findListenerForType(type);
        if (listener != null) {
            return (LeafType) ReflectionHelper.invokeListener(listener, rootLeaf);
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

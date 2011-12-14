package helpers;

import models.cmscore.Leaf;
import play.modules.cmscore.CachedAnnotationListener;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.Listeners;

import java.util.List;

public class ProvidesLeafHelper {

    public static LeafType triggerListener(Class type, Leaf rootLeaf) {

        CachedAnnotationListener listener = findListenerForType(type);
        if (listener != null) {
            Class[] parameterTypes = listener.method.getParameterTypes();
            Object[] parameters = LeafHelper.getInvocationParameters(parameterTypes, rootLeaf);
            return (LeafType) LeafHelper.invokeMethod(listener.method, parameters);
        }
        throw new RuntimeException("Every type must have a class annotated with @Provides to instantiate an instance");
    }
    
    private static CachedAnnotationListener findListenerForType(Class type) {
        List<CachedAnnotationListener> listeners = Listeners.getListenersForAnnotationType(play.modules.cmscore.annotations.Provides.class);
        for (CachedAnnotationListener listener : listeners) {
            if (((play.modules.cmscore.annotations.Provides)listener.annotation).type().equals(type)) {
                return listener;
            }
        }
        return null;
    }

}

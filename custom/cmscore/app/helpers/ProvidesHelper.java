package helpers;

import models.cmscore.RootLeaf;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.Provides;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@Provides listeners. Should not be used directly, use LeafHelper instead.
 * @see LeafHelper
 * @see Provides
 */
public class ProvidesHelper {

    public static Leaf triggerListener(Class type, RootLeaf leaf) {

        CachedAnnotation listener = findListenerForType(type);
        if (listener == null) {
            throw new RuntimeException("Every type must have a class annotated with @Provides to instantiate an instance");
        }

        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Leaf.class, leaf);
        return (Leaf) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    private static CachedAnnotation findListenerForType(final Class type) {
        List<CachedAnnotation> listeners = Listeners.getListenersForAnnotationType(Provides.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                return ((Provides) listener.annotation).type().equals(type);
            }
        });
        if (!listeners.isEmpty()) {
            // TODO: Probably will need a way to override the type
            if (listeners.size() > 1) {
                throw new RuntimeException("Only one @Provides per type is allowed");
            }
            return listeners.iterator().next();
        } else {
            return null;
        }
    }

}

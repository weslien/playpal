package helpers;

import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.Provides;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@Provides listeners. Should not be used directly, use NodeHelper instead.
 *
 * @see NodeHelper
 * @see Provides
 */
public class ProvidesHelper {

    public static <T> T triggerListener(Provides.Type providesType, String withType, Node node) {
        //noinspection unchecked
        return (T) triggerListener(providesType, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static <T> T triggerListener(Provides.Type providesType, String withType, Node node, Class argType, Object arg) {
        //noinspection unchecked
        return (T) triggerListener(providesType, withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static <T> T triggerListener(Provides.Type providesType, String withType, Node node, Map<Class, Object> args) {
        CachedAnnotation listener = findListener(providesType, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Node.class, node);
        parameters.putAll(args);
        //noinspection unchecked
        return (T) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    private static CachedAnnotation findListener(Provides.Type providesType, String withType) {
        CachedAnnotation listener = findListenerForType(providesType, withType);
        if (listener == null) {
            throw new RuntimeException("Every type (specified by using attribute 'with') must have a class annotated with @Provides to instantiate an instance. Unable to find a Provider for type [" + withType + "]");
        }
        return listener;
    }

    private static CachedAnnotation findListenerForType(final Provides.Type type, final String withType) {
        List<CachedAnnotation> listeners = Listeners.getListenersForAnnotationType(Provides.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                Provides annotation = (Provides) listener.annotation;
                return annotation.type().equals(type) && annotation.with().equals(withType);
            }
        });
        if (!listeners.isEmpty()) {
            if (listeners.size() > 1) {
                throw new RuntimeException("Only one @Provides per type (attribute 'with') is allowed");
            }
            return listeners.iterator().next();
        } else {
            return null;
        }
    }
}

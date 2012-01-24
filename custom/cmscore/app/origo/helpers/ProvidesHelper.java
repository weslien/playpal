package origo.helpers;

import play.modules.origo.core.CachedAnnotation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@Provides origo.listeners. Should not be used directly, use NodeHelper instead.
 *
 * @see NodeHelper
 * @see play.modules.origo.core.annotations.Provides
 */
public class ProvidesHelper {

    public static <T> T triggerListener(play.modules.origo.core.annotations.Provides.Type providesType, String withType, play.modules.origo.core.Node node) {
        //noinspection unchecked
        return (T) triggerListener(providesType, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static <T> T triggerListener(play.modules.origo.core.annotations.Provides.Type providesType, String withType, play.modules.origo.core.Node node, Class argType, Object arg) {
        //noinspection unchecked
        return (T) triggerListener(providesType, withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static <T> T triggerListener(play.modules.origo.core.annotations.Provides.Type providesType, String withType, play.modules.origo.core.Node node, Map<Class, Object> args) {
        CachedAnnotation listener = findListener(providesType, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(play.modules.origo.core.Node.class, node);
        parameters.putAll(args);
        //noinspection unchecked
        return (T) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    private static play.modules.origo.core.CachedAnnotation findListener(play.modules.origo.core.annotations.Provides.Type providesType, String withType) {
        play.modules.origo.core.CachedAnnotation listener = findListenerForType(providesType, withType);
        if (listener == null) {
            throw new RuntimeException("Every type (specified by using attribute 'with') must have a class annotated with @Provides to instantiate an instance. Unable to find a Provider for type [" + withType + "]");
        }
        return listener;
    }

    private static play.modules.origo.core.CachedAnnotation findListenerForType(final play.modules.origo.core.annotations.Provides.Type type, final String withType) {
        List<CachedAnnotation> listeners = play.modules.origo.core.Listeners.getListenersForAnnotationType(play.modules.origo.core.annotations.Provides.class, new play.modules.origo.core.Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                play.modules.origo.core.annotations.Provides annotation = (play.modules.origo.core.annotations.Provides) listener.annotation;
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

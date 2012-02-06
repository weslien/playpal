package origo.helpers;

import org.apache.commons.lang.StringUtils;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;

import java.util.*;

/**
 * Helper to trigger \@Provides origo.listeners. Should not be used directly except in core and admin, use NodeHelper
 * instead when creating a new module.
 *
 * @see NodeHelper
 * @see Provides
 */
public class ProvidesHelper {

    public static <T> T triggerListener(String providesType, String withType, Node node) {
        //noinspection unchecked
        return (T) triggerListener(providesType, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static <T> T triggerListener(String providesType, String withType, Node node, Class argType, Object arg) {
        //noinspection unchecked
        return (T) triggerListener(providesType, withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static <T> T triggerListener(String providesType, String withType, Node node, Map<Class, Object> args) {
        CachedAnnotation cachedAnnotation = findListener(providesType, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Node.class, node);
        parameters.putAll(args);
        //noinspection unchecked
        return (T) ReflectionHelper.invokeMethod(cachedAnnotation.method, parameters);
    }

    /**
     * Collects all \@Provides.with for the specified providesType. To be used when choosing a type for a new item for
     * instance or to find all DASHBOARD_ITEM's for the admin module.
     *
     * @param providesType a type to look for (NODE, NAVIGATION, NAVIGATION_ITEM, DASHBOARD_ITEM, etc).
     * @return a list of all "with" added to the system.
     */
    public static Set<String> getAllProvidesWithForType(String providesType) {
        Set<String> providedTypes = new HashSet<String>();
        List<CachedAnnotation> cachedAnnotations = getAllProvidersForType(providesType);
        for (CachedAnnotation cachedAnnotation : cachedAnnotations) {
            providedTypes.add(((Provides) cachedAnnotation.annotation).with());
        }
        return providedTypes;
    }

    private static List<CachedAnnotation> getAllProvidersForType(final String providesType) {
        return Listeners.getListenersForAnnotationType(Provides.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                return ((Provides) listener.annotation).type().equals(providesType);
            }
        });
    }

    private static CachedAnnotation findListener(String providesType, String withType) {
        CachedAnnotation listener = findProvidersForType(providesType, withType);
        if (listener == null) {
            throw new RuntimeException("Every type (specified by using attribute 'with') must have a class annotated with @Provides to instantiate an instance. Unable to find a provider for type \'" + withType + "\'");
        }
        return listener;
    }

    private static CachedAnnotation findProvidersForType(final String type, final String withType) {
        List<CachedAnnotation> providers = Listeners.getListenersForAnnotationType(Provides.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                Provides annotation = (Provides) listener.annotation;
                return annotation.type().equals(type) && (annotation.with().equals(withType) || StringUtils.isBlank(withType));
            }
        });
        if (!providers.isEmpty()) {
            if (providers.size() > 1) {
                throw new RuntimeException("Only one @Provides per type (attribute 'with') is allowed");
            }
            return providers.iterator().next();
        } else {
            return null;
        }
    }
}

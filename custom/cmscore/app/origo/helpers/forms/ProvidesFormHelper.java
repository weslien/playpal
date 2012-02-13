package origo.helpers.forms;

import org.apache.commons.lang.StringUtils;
import origo.helpers.ReflectionHelper;
import origo.helpers.SettingsHelper;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.forms.ProvidesForm;

import java.util.*;

/**
 * Helper to trigger \@Provides origo.listeners. Should not be used directly except in core and admin, use NodeHelper
 * instead when creating a new module.
 *
 * @see origo.helpers.NodeHelper
 * @see play.modules.origo.core.annotations.Provides
 */
public class ProvidesFormHelper {

    public static <T> T triggerListener(String withType, Node node) {
        //noinspection unchecked
        return (T) triggerListener(withType, node, Collections.<Class, Object>emptyMap());
    }

    public static <T> T triggerListener(String withType, Node node, Class argType, Object arg) {
        //noinspection unchecked
        return (T) triggerListener(withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static <T> T triggerListener(String withType, Node node, Map<Class, Object> args) {
        CachedAnnotation cachedAnnotation = findListener(withType);
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
     * @return a list of all "with" added to the system.
     */
    public static Set<String> getAllProvidesForm() {
        Set<String> providedTypes = new HashSet<String>();
        List<CachedAnnotation> cachedAnnotations = Listeners.getListenersForAnnotationType(ProvidesForm.class);
        for (CachedAnnotation cachedAnnotation : cachedAnnotations) {
            providedTypes.add(((ProvidesForm) cachedAnnotation.annotation).with());
        }
        return providedTypes;
    }

    private static CachedAnnotation findListener(String withType) {
        CachedAnnotation listener = findProvidersForType(withType);
        if (listener == null) {
            listener = findProvidersForType(SettingsHelper.Core.getDefaultFormProviderType());
            if (listener == null) {
                throw new RuntimeException("Unable to find a form provider for type \'" + withType + "\' and the default form provider from settings is also not available");
            }
        }
        return listener;
    }

    private static CachedAnnotation findProvidersForType(final String withType) {
        List<CachedAnnotation> providers = Listeners.getListenersForAnnotationType(ProvidesForm.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                ProvidesForm annotation = (ProvidesForm) listener.annotation;
                return (annotation.with().equals(withType) || StringUtils.isBlank(withType));
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

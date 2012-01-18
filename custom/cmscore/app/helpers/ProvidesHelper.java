package helpers;

import models.cmscore.RootNode;
import models.cmscore.Segment;
import models.cmscore.navigation.Navigation;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.UIElement;

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

    public static Node triggerNodeListener(Class withType, RootNode rootNode) {
        CachedAnnotation listener = findListener(Provides.Type.NODE, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Node.class, rootNode);
        return (Node) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    public static UIElement triggerSegmentListener(Class withType, Node node, Segment segment) {
        CachedAnnotation listener = findListener(Provides.Type.SEGMENT, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Node.class, node);
        parameters.put(Segment.class, segment);
        return (UIElement) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    public static UIElement triggerFormListener(Class withType, Node node) {
        CachedAnnotation listener = findListener(Provides.Type.FORM, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Node.class, node);
        return (UIElement) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    public static NavigationElement triggerNavigationListener(Class withType, Node node, Navigation navigation) {
        CachedAnnotation listener = findListener(Provides.Type.NAVIGATION, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Node.class, node);
        parameters.put(Navigation.class, navigation);
        return (NavigationElement) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    private static CachedAnnotation findListener(Provides.Type type, Class withType) {
        CachedAnnotation listener = findListenerForType(type, withType);
        if (listener == null) {
            throw new RuntimeException("Every type (attribute 'with') must have a class annotated with @Provides to instantiate an instance");
        }
        return listener;
    }

    private static CachedAnnotation findListenerForType(final Provides.Type type, final Class withType) {
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

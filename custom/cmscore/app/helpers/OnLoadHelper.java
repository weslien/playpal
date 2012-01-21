package helpers;

import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.OnLoad;
import play.modules.cmscore.ui.UIElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@OnLoad listeners. Should not be used directly, use NodeHelper instead.
 *
 * @see NodeHelper
 * @see play.modules.cmscore.annotations.OnLoad
 */
public class OnLoadHelper {

    public static void triggerBeforeListener(OnLoad.Type type, Node node, Class argType, Object arg) {
        triggerBeforeListener(type, null, node, argType, arg);
    }

    public static void triggerBeforeListener(OnLoad.Type type, Class withType, Node node) {
        triggerBeforeListener(type, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerBeforeListener(OnLoad.Type type, Class withType, Node node, Class argType, Object arg) {
        triggerBeforeListener(type, withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static void triggerBeforeListener(OnLoad.Type type, Class withType, Node node, Map<Class, Object> args) {
        List<CachedAnnotation> listeners = findListenerForType(type, withType != null ? withType : node.getClass(), false);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Node.class, node);
            parameters.putAll(args);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    public static void triggerAfterListener(OnLoad.Type onLoadType, Node node, Class argType, Object arg, UIElement uiElement) {
        triggerAfterListener(onLoadType, null, node, argType, arg, uiElement);
    }

    public static void triggerAfterListener(OnLoad.Type onLoadType, Class withType, Node node, Class argType, Object arg, UIElement uiElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(argType, arg);
        args.put(UIElement.class, uiElement);
        triggerAfterListener(onLoadType, withType, node, args);
    }

    public static void triggerAfterListener(OnLoad.Type onLoadType, Class withType, Node node, UIElement uiElement) {
        triggerAfterListener(onLoadType, withType, node, Collections.<Class, Object>singletonMap(UIElement.class, uiElement));
    }

    public static void triggerAfterListener(OnLoad.Type onLoadType, Class withType, Node node) {
        triggerAfterListener(onLoadType, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerAfterListener(OnLoad.Type onLoadType, Class withType, Node node, Map<Class, Object> args) {
        List<CachedAnnotation> listeners = findListenerForType(onLoadType, withType != null ? withType : node.getClass(), true);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Node.class, node);
            parameters.putAll(args);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final OnLoad.Type onLoadType, final Class withType, final boolean after) {
        return Listeners.getListenersForAnnotationType(OnLoad.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                OnLoad annotation = ((OnLoad) listener.annotation);
                return annotation.type().equals(onLoadType) && (annotation.with() == null || annotation.with().equals(withType) || annotation.with().equals(Object.class)) && annotation.after() == after;
            }
        });
    }

}
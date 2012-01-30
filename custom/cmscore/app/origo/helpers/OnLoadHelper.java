package origo.helpers;

import org.apache.commons.lang.StringUtils;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.ui.UIElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@OnLoad listeners. Should not be used directly, use NodeHelper instead.
 *
 * @see NodeHelper
 * @see OnLoad
 */
public class OnLoadHelper {

    public static void triggerBeforeListener(String type, Node node, Class argType, Object arg) {
        triggerBeforeListener(type, null, node, argType, arg);
    }

    public static void triggerBeforeListener(String type, String withType, Node node) {
        triggerBeforeListener(type, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerBeforeListener(String type, String withType, Node node, Class argType, Object arg) {
        triggerBeforeListener(type, withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static void triggerBeforeListener(String type, String withType, Node node, Map<Class, Object> args) {
        List<CachedAnnotation> listeners = findListenerForType(type, withType, false);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Node.class, node);
            parameters.putAll(args);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    public static void triggerAfterListener(String onLoadType, Node node, Class argType, Object arg, UIElement uiElement) {
        triggerAfterListener(onLoadType, null, node, argType, arg, uiElement);
    }

    public static void triggerAfterListener(String onLoadType, String withType, Node node, Class argType, Object arg, UIElement uiElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(argType, arg);
        args.put(UIElement.class, uiElement);
        triggerAfterListener(onLoadType, withType, node, args);
    }

    public static void triggerAfterListener(String onLoadType, String withType, Node node, UIElement uiElement) {
        triggerAfterListener(onLoadType, withType, node, Collections.<Class, Object>singletonMap(UIElement.class, uiElement));
    }

    public static void triggerAfterListener(String onLoadType, String withType, Node node) {
        triggerAfterListener(onLoadType, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerAfterListener(String onLoadType, String withType, Node node, Map<Class, Object> args) {
        List<CachedAnnotation> listeners = findListenerForType(onLoadType, !StringUtils.isBlank(withType) ? withType : node.getClass().getName(), true);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Node.class, node);
            parameters.putAll(args);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final String onLoadType, final String withType, final boolean after) {
        return Listeners.getListenersForAnnotationType(OnLoad.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                OnLoad annotation = ((OnLoad) listener.annotation);
                return annotation.type().equals(onLoadType) && annotation.after() == after &&
                        (StringUtils.isBlank(annotation.with()) || annotation.with().equals(withType));
            }
        });
    }

}

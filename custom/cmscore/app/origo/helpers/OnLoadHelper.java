package origo.helpers;

import org.apache.commons.lang.StringUtils;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@OnLoad origo.listeners. Should not be used directly, use NodeHelper instead.
 *
 * @see NodeHelper
 * @see play.modules.origo.core.annotations.OnLoad
 */
public class OnLoadHelper {

    public static void triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type type, play.modules.origo.core.Node node, Class argType, Object arg) {
        triggerBeforeListener(type, null, node, argType, arg);
    }

    public static void triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type type, String withType, play.modules.origo.core.Node node) {
        triggerBeforeListener(type, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type type, String withType, play.modules.origo.core.Node node, Class argType, Object arg) {
        triggerBeforeListener(type, withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static void triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type type, String withType, play.modules.origo.core.Node node, Map<Class, Object> args) {
        List<CachedAnnotation> listeners = findListenerForType(type, withType, false);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(play.modules.origo.core.Node.class, node);
            parameters.putAll(args);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    public static void triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type onLoadType, play.modules.origo.core.Node node, Class argType, Object arg, play.modules.origo.core.ui.UIElement uiElement) {
        triggerAfterListener(onLoadType, null, node, argType, arg, uiElement);
    }

    public static void triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type onLoadType, String withType, play.modules.origo.core.Node node, Class argType, Object arg, play.modules.origo.core.ui.UIElement uiElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(argType, arg);
        args.put(play.modules.origo.core.ui.UIElement.class, uiElement);
        triggerAfterListener(onLoadType, withType, node, args);
    }

    public static void triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type onLoadType, String withType, play.modules.origo.core.Node node, play.modules.origo.core.ui.UIElement uiElement) {
        triggerAfterListener(onLoadType, withType, node, Collections.<Class, Object>singletonMap(play.modules.origo.core.ui.UIElement.class, uiElement));
    }

    public static void triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type onLoadType, String withType, play.modules.origo.core.Node node) {
        triggerAfterListener(onLoadType, withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type onLoadType, String withType, play.modules.origo.core.Node node, Map<Class, Object> args) {
        List<play.modules.origo.core.CachedAnnotation> listeners = findListenerForType(onLoadType, !StringUtils.isBlank(withType) ? withType : node.getClass().getName(), true);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(play.modules.origo.core.Node.class, node);
            parameters.putAll(args);
            for (play.modules.origo.core.CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final play.modules.origo.core.annotations.OnLoad.Type onLoadType, final String withType, final boolean after) {
        return Listeners.getListenersForAnnotationType(play.modules.origo.core.annotations.OnLoad.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(play.modules.origo.core.CachedAnnotation listener) {
                play.modules.origo.core.annotations.OnLoad annotation = ((play.modules.origo.core.annotations.OnLoad) listener.annotation);
                return annotation.type().equals(onLoadType) && annotation.after() == after &&
                        (StringUtils.isBlank(annotation.with()) || annotation.with().equals(withType));
            }
        });
    }

}

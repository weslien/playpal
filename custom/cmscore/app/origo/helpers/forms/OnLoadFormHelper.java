package origo.helpers.forms;

import org.apache.commons.lang.StringUtils;
import origo.helpers.ReflectionHelper;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.forms.OnLoadForm;
import play.modules.origo.core.ui.UIElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@OnLoad form listeners. Should not be used directly, use NodeHelper instead.
 *
 * @see origo.helpers.NodeHelper
 * @see play.modules.origo.core.annotations.OnLoad
 */
public class OnLoadFormHelper {

    public static void triggerBeforeListener(Node node, Class argType, Object arg) {
        triggerBeforeListener(null, node, argType, arg);
    }

    public static void triggerBeforeListener(String withType, Node node) {
        triggerBeforeListener(withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerBeforeListener(String withType, Node node, Class argType, Object arg) {
        triggerBeforeListener(withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static void triggerBeforeListener(String withType, Node node, Map<Class, Object> args) {
        List<CachedAnnotation> listeners = findListenerForType(withType, false);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Node.class, node);
            parameters.putAll(args);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    public static void triggerAfterListener(Node node, Class argType, Object arg, UIElement uiElement) {
        triggerAfterListener(null, node, argType, arg, uiElement);
    }

    public static void triggerAfterListener(String withType, Node node, Class argType, Object arg, UIElement uiElement) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(argType, arg);
        args.put(UIElement.class, uiElement);
        triggerAfterListener(withType, node, args);
    }

    public static void triggerAfterListener(String withType, Node node, UIElement uiElement) {
        triggerAfterListener(withType, node, Collections.<Class, Object>singletonMap(UIElement.class, uiElement));
    }

    public static void triggerAfterListener(String withType, Node node) {
        triggerAfterListener(withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerAfterListener(String withType, Node node, Map<Class, Object> args) {
        List<CachedAnnotation> listeners = findListenerForType(!StringUtils.isBlank(withType) ? withType : node.getClass().getName(), true);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Node.class, node);
            parameters.putAll(args);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final String withType, final boolean after) {
        return Listeners.getListenersForAnnotationType(OnLoadForm.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                OnLoadForm annotation = ((OnLoadForm) listener.annotation);
                return annotation.after() == after && (StringUtils.isBlank(annotation.with()) || annotation.with().equals(withType));
            }
        });
    }

}

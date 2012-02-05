package origo.helpers;

import play.Logger;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnPost;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OnPostHelper {

    public static void triggerListener(String withType, Node node) {
        triggerListener(withType, node, Collections.<Class, Object>emptyMap());
    }

    public static void triggerListener(String withType, Node node, Class argType, Object arg) {
        triggerListener(withType, node, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static void triggerListener(String withType, Node node, Map<Class, Object> args) {
        List<CachedAnnotation> cachedAnnotations = findOnPostListsersWithType(withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Node.class, node);
        parameters.putAll(args);
        for (CachedAnnotation cachedAnnotation : cachedAnnotations) {
            ReflectionHelper.invokeMethod(cachedAnnotation.method, parameters);
        }
    }

    private static List<CachedAnnotation> findOnPostListsersWithType(final String withType) {
        List<CachedAnnotation> onPostListeners = Listeners.getListenersForAnnotationType(OnPost.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                OnPost annotation = (OnPost) listener.annotation;
                return annotation.with().equals(withType);
            }
        });
        if (onPostListeners.isEmpty()) {
            Logger.warn("No @OnPost listener for with=" + withType);
        }
        return onPostListeners;
    }


}

package origo.helpers;

import play.Logger;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;
import play.modules.origo.core.annotations.OnSubmit;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OnSubmitHelper {

    public static void triggerListeners(String withType) {
        triggerListeners(withType, Collections.<Class, Object>emptyMap());
    }

    public static void triggerListeners(String withType, Class argType, Object arg) {
        triggerListeners(withType, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static void triggerListeners(String withType, Map<Class, Object> args) {
        List<CachedAnnotation> cachedAnnotations = findOnPostListenersWithType(withType);
        for (CachedAnnotation cachedAnnotation : cachedAnnotations) {
            ReflectionHelper.invokeMethod(cachedAnnotation.method, args);
        }
    }

    private static List<CachedAnnotation> findOnPostListenersWithType(final String withType) {
        List<CachedAnnotation> onPostListeners = Listeners.getListenersForAnnotationType(OnSubmit.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                OnSubmit annotation = (OnSubmit) listener.annotation;
                return annotation.with().equals(withType);
            }
        });
        if (onPostListeners.isEmpty()) {
            Logger.warn("No @OnSubmit listener for with=\'" + withType + "\'");
        }
        return onPostListeners;
    }


}

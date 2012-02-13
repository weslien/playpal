package origo.helpers.forms;

import origo.helpers.ReflectionHelper;
import play.Logger;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;

import java.util.Collections;
import java.util.HashMap;
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
        Map<Class, Object> params = new HashMap<Class, Object>();
        params.putAll(args);
        for (CachedAnnotation cachedAnnotation : cachedAnnotations) {
            ReflectionHelper.invokeMethod(cachedAnnotation.method, params);
        }
    }

    private static List<CachedAnnotation> findOnPostListenersWithType(final String withType) {
        List<CachedAnnotation> onPostListeners = Listeners.getListenersForAnnotationType(play.modules.origo.core.annotations.forms.OnSubmit.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                play.modules.origo.core.annotations.forms.OnSubmit annotation = (play.modules.origo.core.annotations.forms.OnSubmit) listener.annotation;
                return annotation.with().equals(withType);
            }
        });
        if (onPostListeners.isEmpty()) {
            Logger.warn("No @OnSubmit listener for with=\'" + withType + "\'");
        }
        return onPostListeners;
    }


}

package origo.helpers.forms;

import origo.helpers.ReflectionHelper;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubmitStateHelper {

    public static void triggerListener(String state, String withType) {
        triggerListener(state, withType, Collections.<Class, Object>emptyMap());
    }

    public static void triggerListener(String state, String withType, Class argType, Object arg) {
        triggerListener(state, withType, Collections.<Class, Object>singletonMap(argType, arg));
    }

    public static void triggerListener(String state, String withType, Map<Class, Object> args) {
        CachedAnnotation cachedAnnotation = findOnPostListenersWithType(state, withType);
        Map<Class, Object> params = new HashMap<Class, Object>();
        params.putAll(args);
        ReflectionHelper.invokeMethod(cachedAnnotation.method, params);
    }

    private static CachedAnnotation findOnPostListenersWithType(final String state, final String withType) {
        List<CachedAnnotation> submitStateListeners = Listeners.getListenersForAnnotationType(play.modules.origo.core.annotations.forms.SubmitState.class, new CachedAnnotation.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                play.modules.origo.core.annotations.forms.SubmitState annotation = (play.modules.origo.core.annotations.forms.SubmitState) listener.annotation;
                return annotation.state().equals(state) && annotation.with().equals(withType);
            }
        });
        if (submitStateListeners.isEmpty()) {
            throw new RuntimeException("Every form type (specified by using attribute 'with') must have a class annotated with @SubmitState to use as an endpoint for submit\'s. Unable to find a SubmitState for state=\'" + state + "\' and type=\'" + withType + "\'");
        }
        if (submitStateListeners.size() > 1) {
            throw new RuntimeException("Only one @SubmitState(state=\'" + state + "\') per type (attribute 'with') is allowed");
        }
        return submitStateListeners.iterator().next();
    }

}

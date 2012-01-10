package helpers;

import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.FormEnd;
import play.modules.cmscore.ui.UIElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormEndHelper {

    public static void triggerListener(String formId, UIElement formElement) {
        List<CachedAnnotation> listeners = findListenerForType(formId);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(String.class, formId);
            parameters.put(UIElement.class, formElement);
            for(CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final String formId) {
        return Listeners.getListenersForAnnotationType(FormEnd.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                return ((FormEnd) listener.annotation).id().equals(formId);
            }
        });
    }

}

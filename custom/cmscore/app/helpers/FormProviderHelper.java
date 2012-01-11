package helpers;

import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.FormProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormProviderHelper {

    public static void triggerListener(Leaf leaf) {
        List<CachedAnnotation> listeners = findListenerForType(leaf.getClass());
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Leaf.class, leaf);
            for(CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final Class leafType) {
        return Listeners.getListenersForAnnotationType(FormProvider.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                return ((FormProvider) listener.annotation).type().equals(leafType);
            }
        });
    }

}

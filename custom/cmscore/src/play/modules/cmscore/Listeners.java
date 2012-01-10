package play.modules.cmscore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Listeners {

    public static Map<Class<? extends Annotation>, List<CachedAnnotation>> listeners = new HashMap<Class<? extends Annotation>, List<CachedAnnotation>>();

    public static void addListener(Annotation annotation, Method method) {
        if (!listeners.containsKey(annotation.annotationType())) {
            listeners.put(annotation.annotationType(), new CopyOnWriteArrayList<CachedAnnotation>());
        }
        List<CachedAnnotation> annotationTypes = listeners.get(annotation.annotationType());
        annotationTypes.add(new CachedAnnotation(annotation, method));
    }
    
    public static List<CachedAnnotation> getListenersForAnnotationType(Class<? extends Annotation> annotationType) {
        return getListenersForAnnotationType(annotationType, null);
    }

    public static List<CachedAnnotation> getListenersForAnnotationType(Class<? extends Annotation> annotationType, ListenerSelector listenerSelector) {
        if (listeners.containsKey(annotationType)) {
            List<CachedAnnotation> listenerList = listeners.get(annotationType);
            if (listenerSelector == null) {
                return listenerList;
            }
            List<CachedAnnotation> matchingListeners = new ArrayList<CachedAnnotation>();
            for (CachedAnnotation listener : listenerList) {
                if (listenerSelector.isCorrectListener(listener)) {
                    matchingListeners.add(listener);
                }
            }
            return matchingListeners;
        } else {
            return Collections.emptyList();
        }
    }

    // TODO: invalidation breaks everything so it is commented out for now
    public static void invalidate(Class cls) {
        /*
        for (List<CachedAnnotation> annotationTypes : listeners.values()) {
            List<CachedAnnotation> toBeRemoved = new ArrayList<CachedAnnotation>();
            for (CachedAnnotation listener : annotationTypes) {
                if (listener.method.getDeclaringClass().equals(cls)) {
                    toBeRemoved.add(listener);
                }
            }
            annotationTypes.removeAll(toBeRemoved);
        }
        */
    }

    public interface ListenerSelector {

        boolean isCorrectListener(CachedAnnotation listener);

    }
}

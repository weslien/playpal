package play.modules.cmscore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Listeners {
    
    public static Map<Class<? extends Annotation>, List<CachedAnnotationListener>> listeners = new ConcurrentHashMap<Class<? extends Annotation>, List<CachedAnnotationListener>>();

    public static void addListener(Annotation annotation, Method method) {
        if (!listeners.containsKey(annotation.annotationType())) {
            listeners.put(annotation.annotationType(), new ArrayList<CachedAnnotationListener>());
        }
        List<CachedAnnotationListener> annotationTypeListeners = listeners.get(annotation.annotationType());
        annotationTypeListeners.add(new CachedAnnotationListener(annotation, method));
    }

    public static List<CachedAnnotationListener> getListenersForAnnotationType(Class<? extends Annotation> annotationType) {
        if (listeners.containsKey(annotationType)) {
            return listeners.get(annotationType);
        } else {
            return Collections.emptyList();
        }
    }

    public static void invalidate(Class cls) {
        for (List<CachedAnnotationListener> annotationTypeListeners : listeners.values()) {
            for (Iterator<CachedAnnotationListener> listenerIterator = annotationTypeListeners.iterator(); listenerIterator.hasNext(); ) {
                CachedAnnotationListener listener = listenerIterator.next();
                if (listener.method.getDeclaringClass().equals(cls)) {
                    listenerIterator.remove();
                }
            }
        }
    }

}

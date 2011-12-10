package play.modules.cmscore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Listeners {
    
    public static Map<Class<? extends Annotation>, List<CachedAnnotationListener>> listeners = new ConcurrentHashMap<Class<? extends Annotation>, List<CachedAnnotationListener>>();

    public static void addListener(Annotation annotation, Method method) {
        if (!listeners.containsKey(annotation.getClass())) {
            listeners.put(annotation.getClass(), new ArrayList<CachedAnnotationListener>());
        }
        List<CachedAnnotationListener> annotationTypeListeners = listeners.get(annotation.getClass());
        annotationTypeListeners.add(new CachedAnnotationListener(annotation, method));
    }

    public static List<CachedAnnotationListener> getListenersForAnnotationType(Class<? extends Annotation> annotationType) {
        if (listeners.containsKey(annotationType)) {
            return listeners.get(annotationType);
        } else {
            return Collections.<CachedAnnotationListener>EMPTY_LIST;
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

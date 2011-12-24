package play.modules.cmscore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class Listeners {
    
    public static Map<Class<? extends Annotation>, List<CachedAnnotation>> listeners = new HashMap<Class<? extends Annotation>, List<CachedAnnotation>>();

    public static void addListener(Annotation annotation, Method method) {
        if (!listeners.containsKey(annotation.annotationType())) {
            listeners.put(annotation.annotationType(), new ArrayList<CachedAnnotation>());
        }
        List<CachedAnnotation> annotationTypes = listeners.get(annotation.annotationType());
        annotationTypes.add(new CachedAnnotation(annotation, method));
    }

    public static List<CachedAnnotation> getListenersForAnnotationType(Class<? extends Annotation> annotationType) {
        if (listeners.containsKey(annotationType)) {
            //noinspection unchecked
            return listeners.get(annotationType);
        } else {
            return Collections.emptyList();
        }
    }
    
    public static void invalidate(Class cls) {
        for (List<CachedAnnotation> annotationTypes : listeners.values()) {
            for (Iterator<CachedAnnotation> listenerIterator = annotationTypes.iterator(); listenerIterator.hasNext(); ) {
                CachedAnnotation listener = listenerIterator.next();
                if (listener.method.getDeclaringClass().equals(cls)) {
                    listenerIterator.remove();
                }
            }
        }
    }

}

package play.modules.origo.admin;

import org.apache.commons.lang.StringUtils;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.CachedAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Admins {

    public static Set<String> pages = new HashSet<String>();
    public static Map<String, CachedAnnotation> aliases = new HashMap<String, CachedAnnotation>();
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

    public static List<CachedAnnotation> getListenersForAnnotationType(Class<? extends Annotation> annotationType, CachedAnnotation.ListenerSelector listenerSelector) {
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

    public static void invalidate() {
        listeners.clear();
        pages.clear();
        aliases.clear();
    }

    public static void addPage(Admin.Page annotation, Method m) {
        if (StringUtils.isBlank(annotation.name())) {
            throw new RuntimeException("Admin.Page can not have an empty name attribute");
        }
        if (pages.contains(StringUtils.trim(annotation.name()))) {
            throw new RuntimeException("Admin.Page must have a unique name attribute");
        }
        pages.add(annotation.name());
        if (StringUtils.isBlank(annotation.alias())) {
            aliases.put(annotation.name(), new CachedAnnotation(annotation, m));
        } else {
            aliases.put(annotation.alias(), new CachedAnnotation(annotation, m));
        }
    }

}

package play.modules.cmscore;

import play.modules.cmscore.annotations.Theme;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Themes {

    public static Map<Class<? extends Annotation>, List<CachedAnnotation>> decorators = new ConcurrentHashMap<Class<? extends Annotation>, List<CachedAnnotation>>();

    public static void addDecorator(Theme theme, Annotation annotation, Method method) {
        if (!decorators.containsKey(annotation.annotationType())) {
            decorators.put(annotation.annotationType(), new ArrayList<CachedAnnotation>());
        }
        List<CachedAnnotation> annotationTypes = decorators.get(annotation.annotationType());
        annotationTypes.add(new ThemeCachedAnnotation(theme, annotation, method));
    }

    public static List<CachedAnnotation> getListenersForAnnotationType(Class<? extends Annotation> annotationType) {
        if (decorators.containsKey(annotationType)) {
            //noinspection unchecked
            return decorators.get(annotationType);
        } else {
            return Collections.emptyList();
        }
    }

    public static void invalidate(Class cls) {
        AnnotationPluginHelper.invalidate(cls, decorators.values());
    }

}

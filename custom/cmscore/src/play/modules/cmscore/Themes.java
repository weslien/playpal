package play.modules.cmscore;

import play.modules.cmscore.annotations.Decorate;
import play.modules.cmscore.annotations.DecorateType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Themes {

    public static Map<String, List<CachedAnnotation>> themes = new ConcurrentHashMap<String, List<CachedAnnotation>>();
    public static Map<DecorateType, List<CachedAnnotation>> decorators = new ConcurrentHashMap<DecorateType, List<CachedAnnotation>>();

    public static void addTheme(String themeId, Method templateMethod, String[] contentAreas) {

    }
    
    public static void addDecorator(Decorate annotation, Method method) {
        if (!decorators.containsKey(annotation.type())) {
            decorators.put(annotation.type(), new ArrayList<CachedAnnotation>());
        }
        List<CachedAnnotation> annotationTypes = decorators.get(annotation.type());
        annotationTypes.add(new CachedAnnotation(annotation, method));
    }

    public static List<CachedAnnotation> getListenersForAnnotationType(DecorateType decorateType) {
        if (decorators.containsKey(decorateType)) {
            return decorators.get(decorateType);
        } else {
            return Collections.emptyList();
        }
    }

    public static void invalidate(Class cls) {
        AnnotationPluginHelper.invalidate(cls, decorators.values());
    }

}

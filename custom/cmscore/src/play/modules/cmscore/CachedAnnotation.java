package play.modules.cmscore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CachedAnnotation {
    
    public final Annotation annotation;
    public final Method method;

    public CachedAnnotation(Annotation annotation, Method method) {
        this.annotation = annotation;
        this.method = method;
    }
}

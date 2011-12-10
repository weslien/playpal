package play.modules.cmscore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CachedAnnotationListener {
    
    public Annotation annotation;
    public Method method;

    public CachedAnnotationListener(Annotation annotation, Method method) {
        this.annotation = annotation;
        this.method = method;
    }
}

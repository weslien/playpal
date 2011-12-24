package play.modules.cmscore.annotations;

import java.lang.reflect.Method;

public class CachedDecorator {

    public final Method method;

    public CachedDecorator(Method method) {
        this.method = method;
    }

}
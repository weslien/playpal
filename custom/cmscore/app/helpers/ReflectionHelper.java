package helpers;

import models.cmscore.Leaf;
import play.Logger;
import play.modules.cmscore.CachedAnnotationListener;
import play.modules.cmscore.LeafType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {

    public static Object invokeListener(CachedAnnotationListener listener, LeafType rootLeaf) {
        Class[] parameterTypes = listener.method.getParameterTypes();
        Object[] parameters = getInvocationParameters(parameterTypes, rootLeaf);
        return invokeMethod(listener.method, parameters);
    }

    @SuppressWarnings("unchecked")
    public static Object[] getInvocationParameters(Class[] parameterTypes, LeafType rootLeaf) {
        List parameters = new ArrayList();

        for (Class parameterType : parameterTypes) {
            if (parameterType.equals(Leaf.class)) {
                parameters.add(rootLeaf);
            } else {
                throw new RuntimeException("Unable to find parameter of type ["+parameterType.getName()+"]");
            }
        }

        return parameters.toArray();
    }

    public static Object invokeMethod(Method m, Object... parameters) {
        try {
            return m.invoke(m.getDeclaringClass(), parameters);
        } catch (IllegalAccessException e) {
            Logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            Logger.error(e.getTargetException().getMessage(), e);
            throw new RuntimeException(e.getTargetException().getMessage(), e);
        }
    }

}

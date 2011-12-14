package helpers;


import models.cmscore.Leaf;
import play.Logger;
import play.modules.cmscore.CachedAnnotationListener;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.annotations.LeafLoaded;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LeafHelper {

    public static LeafType triggerProvidesListener(Class type, Leaf rootLeaf) {
        return ProvidesLeafHelper.triggerListener(type, rootLeaf);
    }

    public static void triggerAfterLeafLoaded(Class type, LeafType leaf) {
        LeafLoadedHelper.triggerListener(type, leaf, LeafLoaded.Order.AFTER);
    }

    public static void triggerBeforeLeafLoaded(Class type, Leaf leaf) {
        LeafLoadedHelper.triggerListener(type, leaf, LeafLoaded.Order.BEFORE);
    }

    static void invokeListener(LeafType rootLeaf, CachedAnnotationListener listener) {
        Class[] parameterTypes = listener.method.getParameterTypes();
        Object[] parameters = LeafHelper.getInvocationParameters(parameterTypes, rootLeaf);
        LeafHelper.invokeMethod(listener.method, parameters);
    }

    @SuppressWarnings("unchecked")
    static Object[] getInvocationParameters(Class[] parameterTypes, LeafType rootLeaf) {
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

    static Object invokeMethod(Method m, Object... parameters) {
        try {
            return m.invoke(m.getDeclaringClass(), parameters);
        } catch (IllegalAccessException e) {
            Logger.error("", e);
            throw new RuntimeException("", e);
        } catch (InvocationTargetException e) {
            Logger.error("", e);
            throw new RuntimeException("", e);
        }
    }
}

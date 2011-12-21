package helpers;

import models.cmscore.Leaf;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.LeafType;
import play.utils.Java;

import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {

    public static Object invokeListener(CachedAnnotation listener, LeafType rootLeaf) {
        Class[] parameterTypes = listener.method.getParameterTypes();
        Object[] parameters = getInvocationParameters(parameterTypes, rootLeaf);
        try {
            return Java.invokeStatic(listener.method, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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

}

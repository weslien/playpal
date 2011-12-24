package helpers;

import models.cmscore.RootLeaf;
import play.modules.cmscore.Leaf;
import play.utils.Java;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for invoking the triggers/listeners/hooks.
 */
public class ReflectionHelper {

    /**
     * Uses reflection to invoke the annotated method.
     * @param method a cached annotated method
     * @param rootLeaf the root rootLeaf of this request
     * @return whatever object the listener/trigger/hook returns, if any.
     */
    public static Object invokeMethod(Method method, Leaf rootLeaf) {
        Class[] parameterTypes = method.getParameterTypes();
        Object[] parameters = getInvocationParameters(parameterTypes, rootLeaf);
        try {
            return Java.invokeStatic(method, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static String invokeDecorator(Method method, Leaf rootLeaf) {
        return (String) invokeMethod(method, rootLeaf);
    }

    /**
     * Tries to match up the parameter types to known entities and constructs the list of parameters.
     * @param parameterTypes an array of types this trigger method takes
     * @param rootLeaf the root rootLeaf of this request
     * @return a list of parameters matching the parameter types
     */
    @SuppressWarnings("unchecked")
    public static Object[] getInvocationParameters(Class[] parameterTypes, Leaf rootLeaf) {
        List parameters = new ArrayList();

        for (Class parameterType : parameterTypes) {
            if (parameterType.equals(RootLeaf.class)) {
                parameters.add(rootLeaf);
            } else {
                throw new RuntimeException("Unable to find parameter of type ["+parameterType.getName()+"]");
            }
        }

        return parameters.toArray();
    }

}

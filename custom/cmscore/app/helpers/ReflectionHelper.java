package helpers;

import models.cmscore.Leaf;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.LeafType;
import play.utils.Java;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods for invoking the triggers/listeners/hooks.
 */
public class ReflectionHelper {

    /**
     * Uses reflection to invoke the annotated method.
     * @param listener a cached annotated method
     * @param rootLeaf the root leaf of this request
     * @return whatever object the listener/trigger/hook returns, if any.
     */
    public static Object invokeListener(CachedAnnotation listener, LeafType rootLeaf) {
        Class[] parameterTypes = listener.method.getParameterTypes();
        Object[] parameters = getInvocationParameters(parameterTypes, rootLeaf);
        try {
            return Java.invokeStatic(listener.method, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Tries to match up the parameter types to known entities and constructs the list of parameters.
     * @param parameterTypes an array of types this trigger method takes
     * @param rootLeaf the root leaf of this request
     * @return a list of parameters matching the parameter types
     */
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

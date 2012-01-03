package helpers;

import play.modules.cmscore.CachedThemeVariant;
import play.modules.cmscore.annotations.CachedDecorator;
import play.utils.Java;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Helper methods for invoking the triggers/listeners/hooks.
 */
public class ReflectionHelper {

    /**
     * Uses reflection to invoke the annotated method.
     * @param method a cached annotated method
     * @param parameters possible parameters for this invocation
     * @return whatever object the listener/trigger/hook returns, if any.
     */
    public static Object invokeMethod(Method method, Map<Class, Object> parameters) {
        Class[] parameterTypes = method.getParameterTypes();
        Object[] foundParameters = getInvocationParameters(parameterTypes, parameters);
        try {
            return Java.invokeStatic(method, foundParameters);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    /**
     * Tries to match up the parameter types to known entities and constructs the array of parameters.
     * @param parameterTypes an array of types this trigger method takes
     * @param parameters possible parameters for this invocation
     * @return a list of parameters matching the parameter types
     */
    @SuppressWarnings("unchecked")
    public static Object[] getInvocationParameters(Class[] parameterTypes, Map<Class, Object> parameters) {
        List foundParameters = new ArrayList();

        for (Class parameterType : parameterTypes) {
            Object parameter = findParameter(parameters, parameterType);
            if (parameter != null) {
                foundParameters.add(parameter);
            }
        }

        return foundParameters.toArray();
    }

    //TODO: Cache this instead of lookin it up every time
    private static Object findParameter(Map<Class, Object> parameters, Class parameterType) {
        if (!Object.class.equals(parameterType)) {
            // Check if the parameter map contains the class from parameterType
            Object parameter = parameters.get(parameterType);
            if (parameter != null) {
                return parameter;
            } else {
                // Check all interfaces of parameterType
                for (Class cls : parameterType.getInterfaces()) {
                    parameter = findParameter(parameters, cls);
                    if (parameter != null) {
                        return parameter;
                    }
                }
                // Check super class of parameterType
                return findParameter(parameters, parameterType.getSuperclass());
            }
        }
        throw new RuntimeException("Unable to find parameter of type ["+parameterType.getName()+"]");
    }

    public static String invokeDecorator(CachedDecorator decorator, Map<Class, Object> parameters) {
        return (String) invokeMethod(decorator.method, parameters);
    }

    public static String getTemplate(CachedThemeVariant themeVariant) {
        try {
            return (String)invokeMethod(themeVariant.templateMethod, null);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

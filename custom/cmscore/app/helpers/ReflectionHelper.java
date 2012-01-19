package helpers;

import play.modules.cmscore.CachedThemeVariant;
import play.modules.cmscore.annotations.CachedDecorator;
import play.utils.Java;

import java.lang.reflect.InvocationTargetException;
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
     *
     * @param method     a cached annotated method
     * @param parameters possible parameters for this invocation
     * @return whatever object the listener/trigger/hook returns, if any.
     */
    public static Object invokeMethod(Method method, Map<Class, Object> parameters) {
        Class[] parameterTypes = method.getParameterTypes();
        Object[] foundParameters = new Object[0];
        try {
            foundParameters = getInvocationParameters(parameterTypes, parameters);
        } catch (UnknownParameterTypeException e) {
            throw new RuntimeException("Method [" + method + "] has a parameter of type [" + e.getParameterType() + "] specified. No parameter of that type exists in the calling parameters");
        }
        try {
            return Java.invokeStatic(method, foundParameters);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException().getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Tries to match up the parameter types to known entities and constructs the array of parameters.
     *
     * @param parameterTypes an array of types this trigger method takes
     * @param parameters     possible parameters for this invocation
     * @return a list of parameters matching the parameter types
     * @throws UnknownParameterTypeException is thrown when there is no matching parameter for a specified parameter type in the method
     */
    @SuppressWarnings("unchecked")
    public static Object[] getInvocationParameters(Class[] parameterTypes, Map<Class, Object> parameters) throws UnknownParameterTypeException {
        List foundParameters = new ArrayList();

        for (Class parameterType : parameterTypes) {
            Object parameter = findParameter(parameters, parameterType);
            if (parameter != null) {
                foundParameters.add(parameter);
            }
        }

        return foundParameters.toArray();
    }

    //TODO: Cache this instead of looking it up every time
    private static Object findParameter(Map<Class, Object> parameters, Class parameterType) throws UnknownParameterTypeException {
        if (!Object.class.equals(parameterType) && parameterType != null) {
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
                try {
                    return findParameter(parameters, parameterType.getSuperclass());
                } catch (UnknownParameterTypeException e) {
                    throw new UnknownParameterTypeException("No parameter of type [" + parameterType + "] was found and it has no super class or interface", parameterType);
                }
            }
        }
        throw new UnknownParameterTypeException(null);
    }

    public static String invokeDecorator(CachedDecorator decorator, Map<Class, Object> parameters) {
        return (String) invokeMethod(decorator.method, parameters);
    }

    public static String getTemplate(CachedThemeVariant themeVariant) {
        try {
            return (String) invokeMethod(themeVariant.templateMethod, null);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

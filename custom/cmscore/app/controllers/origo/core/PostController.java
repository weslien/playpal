package controllers.origo.core;

import org.apache.commons.lang.StringUtils;
import origo.helpers.ReflectionHelper;
import origo.helpers.SettingsHelper;
import play.modules.origo.core.CachedAnnotation;
import play.modules.origo.core.Listeners;
import play.modules.origo.core.annotations.PostHandler;
import play.mvc.Controller;
import play.mvc.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostController extends Controller {

    public static void submit() {

        final String postHandlerName = getRegisteredPostHandlerName();
        CachedAnnotation cachedAnnotation = getPostHandler(postHandlerName);
        invokePostHandler(cachedAnnotation);
    }

    public static Class getActivePostHandler() {
        final String postHandlerName = getRegisteredPostHandlerName();
        CachedAnnotation cachedAnnotation = getPostHandler(postHandlerName);
        return cachedAnnotation.method.getDeclaringClass();
    }

    private static void invokePostHandler(CachedAnnotation cachedAnnotation) {
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Scope.Params.class, params);
        ReflectionHelper.invokeMethod(cachedAnnotation.method, parameters);
    }

    private static String getRegisteredPostHandlerName() {
        final String postHandler = SettingsHelper.Core.getPostHandler();
        if (StringUtils.isBlank(postHandler)) {
            throw new RuntimeException("No PostHandler defined in the settings");
        }
        return postHandler;
    }

    // TODO: Cache this instead of looking it up every time
    private static CachedAnnotation getPostHandler(final String postHandler) {
        List<CachedAnnotation> postHandlers = Listeners.
                getListenersForAnnotationType(PostHandler.class, new CachedAnnotation.ListenerSelector() {
                    @Override
                    public boolean isCorrectListener(CachedAnnotation listener) {
                        return postHandler.equals(listener.method.getDeclaringClass().getName());
                    }
                });

        if (postHandlers.isEmpty()) {
            throw new RuntimeException("No PostHandlers found");
        }
        return postHandlers.iterator().next();
    }

}

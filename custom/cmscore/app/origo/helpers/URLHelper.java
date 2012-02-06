package origo.helpers;

import play.mvc.Router;

import java.util.HashMap;
import java.util.Map;

public class URLHelper {

    public static String getReverseURL(Class action, String method) {
        return getReverseURL(action, method, new HashMap<String, Object>());
    }

    public static String getReverseURL(Class action, String method, Map<String, Object> args) {
        Router.ActionDefinition actionDefinition = Router.reverse(action.getName() + "." + method, args);
        if (actionDefinition != null) {
            return actionDefinition.url;
        }
        return null;
    }

}

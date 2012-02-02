package origo.helpers;

import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Router;

import java.util.HashMap;
import java.util.Map;

public class AdminHelper {

    public static String getURLForAdminAction(String type) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("type", type);
        return getURLForAdminAction(args);
    }

    public static String getURLForAdminAction(String type, String identifier) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("type", type);
        args.put("identifier", identifier);
        return getURLForAdminAction(args);
    }

    private static String getURLForAdminAction(Map<String, Object> args) {
        Router.ActionDefinition actionDefinition = Router.reverse("origo.admin.Application.page", args);
        if (actionDefinition != null) {
            return actionDefinition.url;
        }
        return null;
    }

    /*
    * Convenience methods for hooks with DASHBOARD_ITEM type
    */
    public static UIElement triggerProvidesDashboardItemListener(String withType, Node node) {
        return ProvidesHelper.triggerListener(Admin.DASHBOARD, withType, node);
    }

    public static void triggerBeforeDashboardItemLoaded(String withType, Node node) {
        OnLoadHelper.triggerBeforeListener(Admin.DASHBOARD, withType, node);
    }

    public static void triggerAfterDashboardItemLoaded(String withType, Node node) {
        OnLoadHelper.triggerAfterListener(Admin.DASHBOARD, withType, node);
    }

}

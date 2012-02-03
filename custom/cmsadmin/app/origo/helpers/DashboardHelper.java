package origo.helpers;

import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;

public class DashboardHelper {

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
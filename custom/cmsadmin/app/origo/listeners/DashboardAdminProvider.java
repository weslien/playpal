package origo.listeners;

import models.origo.admin.AdminPage;
import models.origo.core.RootNode;
import origo.helpers.AdminHelper;
import origo.helpers.ProvidesHelper;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;

import java.util.Set;

public class DashboardAdminProvider {

    public static final String TYPE = "origo.admin.dashboard";

    //@Admin.Page(name = START_PAGE)
    @Provides(type = Provides.NODE, with = TYPE)
    public static Node createPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("Dashboard");
        page.rootNode = rootNode;
        return page;
    }

    @OnLoad(type = Provides.NODE, with = DashboardAdminProvider.TYPE)
    public static void addContent(Node node) {

        Set<String> dashboardItemNames = ProvidesHelper.getAllProvidesWithForType(Admin.DASHBOARD);

        for (String dashboardItemName : dashboardItemNames) {
            AdminHelper.triggerBeforeDashboardItemLoaded(dashboardItemName, node);
            UIElement uiElement = AdminHelper.triggerProvidesDashboardItemListener(dashboardItemName, node);
            AdminHelper.triggerAfterDashboardItemLoaded(dashboardItemName, node);
            node.addUIElement(uiElement);
        }

    }

}

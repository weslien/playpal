package origo.listeners;

import models.origo.admin.AdminPage;
import models.origo.core.RootNode;
import origo.helpers.AdminHelper;
import origo.helpers.ProvidesHelper;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.annotations.Types;

import java.util.Set;

/**
 * Dashboard is the front page of the admin UI. Any \@Provides annotation with type DASHBOARD will be picked up and
 * displayed along with the others. This is the default implementation of a DashBoardProvider. If a module would
 * like to change this behaviour it can be done by changing the dashboard type in settings.
 */
public class DefaultDashboardProvider {

    public static final String TYPE = "origo.admin.dashboard";

    @Provides(type = Types.NODE, with = TYPE)
    public static Node createPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("Dashboard");
        page.rootNode = rootNode;
        return page;
    }

    @OnLoad(type = Types.NODE, with = DefaultDashboardProvider.TYPE)
    public static void addContent(Node node) {

        Set<String> dashboardItemNames = ProvidesHelper.getAllProvidesWithForType(Admin.DASHBOARD);

        for (String dashboardItemName : dashboardItemNames) {
            node.addUIElement(AdminHelper.createDashboardItem(dashboardItemName, node));
        }

    }

}

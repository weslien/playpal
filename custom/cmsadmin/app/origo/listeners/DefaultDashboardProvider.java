package origo.listeners;

import models.origo.admin.AdminPage;
import models.origo.core.RootNode;
import origo.helpers.AdminHelper;
import origo.helpers.ProvidesHelper;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;

import java.util.Set;

public class DefaultDashboardProvider {

    public static final String TYPE = "origo.admin.dashboard";

    @Provides(type = Provides.NODE, with = TYPE)
    public static Node createPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("Dashboard");
        page.rootNode = rootNode;
        return page;
    }

    @OnLoad(type = Provides.NODE, with = DefaultDashboardProvider.TYPE)
    public static void addContent(Node node) {

        Set<String> dashboardItemNames = ProvidesHelper.getAllProvidesWithForType(Admin.DASHBOARD);

        for (String dashboardItemName : dashboardItemNames) {
            node.addUIElement(AdminHelper.createDashboardItem(dashboardItemName, node));
        }

    }

}

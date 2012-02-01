package origo.listeners;

import models.origo.admin.AdminPage;
import models.origo.core.RootNode;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;

public class DashboardAdminProvider {

    public static final String TYPE = "origo.admin.dashboard";

    public static final String START_PAGE = "dashboard";

    @Admin.Page(name = START_PAGE)
    @Provides(type = Provides.NODE, with = TYPE)
    public static Node createPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("Dashboard");
        page.rootNode = rootNode;
        return page;
    }

}

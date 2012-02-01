package origo.listeners;

import models.origo.admin.AdminPage;
import models.origo.core.RootNode;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;

public class BasicPageAdminProvider {

    public static final String TYPE = "origo.admin.basicpage";

    @Admin.Page(name = "edit")
    @Provides(type = Provides.NODE, with = TYPE)
    public static Node createPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("Basic Page Admin");
        page.rootNode = rootNode;
        return page;
    }

}

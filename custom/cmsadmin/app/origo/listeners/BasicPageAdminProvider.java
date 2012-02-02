package origo.listeners;

import models.origo.admin.AdminPage;
import models.origo.core.BasicPage;
import models.origo.core.RootNode;
import origo.helpers.AdminHelper;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;

import java.util.List;

public class BasicPageAdminProvider {

    private static final String PAGE_TYPE_PREFIX = "origo.admin.basicpage";

    @Provides(type = Provides.NODE, with = PAGE_TYPE_PREFIX + ".edit")
    public static Node createEditPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("Edit Basic Page");
        page.rootNode = rootNode;
        return page;
    }

    @Provides(type = Provides.NODE, with = PAGE_TYPE_PREFIX + ".list")
    public static Node createListPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("List Basic Pages");
        page.rootNode = rootNode;
        return page;
    }

    //@Admin.Page(name = "list")
    @OnLoad(type = Provides.NODE, with = PAGE_TYPE_PREFIX + ".list")
    public static void createListPage(Node node) {
        List<BasicPage> basicPages = BasicPage.findAll();

        UIElement panelElement = new UIElement(UIElement.PANEL, 10).addAttribute("class", "panel pages");
        for (BasicPage page : basicPages) {
            String editURL = AdminHelper.getURLForAdminAction(PAGE_TYPE_PREFIX + ".edit", page.getNodeId());
            UIElement element = new UIElement(UIElement.ANCHOR, 10, page.getTitle()).addAttribute("href", editURL);
            panelElement.addChild(element);
        }
        node.addUIElement(panelElement);
    }

    //@Admin.Page(name = "edit")
    @OnLoad(type = Provides.NODE, with = PAGE_TYPE_PREFIX)
    public static void createEditPage(Node node, String identifier) {
        BasicPage basicPage = BasicPage.findLatestVersion(identifier);
        if (basicPage == null) {
            node.addUIElement(new UIElement(UIElement.TEXT, 10, "Page '" + identifier + "' does not exist."));
            return;
        }

        UIElement formElement = new UIElement("basicpageform", UIElement.FORM).addAttribute("class", "origo-basicpageform, form");

        UIElement namePanelElement = new UIElement(UIElement.PANEL, 10).addAttribute("class", "field");
        namePanelElement.addChild(new UIElement(UIElement.LABEL, 10, "Title").addAttribute("for", "origo-basicpageform-title"));
        namePanelElement.addChild(new UIElement(UIElement.INPUT_TEXT, 20).addAttribute("name", "origo-basicpageform-title")).addAttribute("value", basicPage.getTitle());
        formElement.addChild(namePanelElement);

        node.addUIElement(formElement);
    }

    @Provides(type = Admin.DASHBOARD, with = "origo.admin.basicpage.dashboard")
    public static UIElement createDashboardItem() {

        String url = AdminHelper.getURLForAdminAction(PAGE_TYPE_PREFIX + ".list");

        UIElement linkElement = new UIElement(UIElement.ANCHOR, 10, "List").addAttribute("href", url).addAttribute("class", "dashboard item link");
        UIElement dashboardElement = new UIElement(Admin.DASHBOARD, 10, "Pages").addChild(linkElement);

        return dashboardElement;
    }

}

package origo.listeners;

import models.origo.admin.AdminPage;
import models.origo.core.BasicPage;
import models.origo.core.Content;
import models.origo.core.RootNode;
import origo.helpers.AdminHelper;
import origo.helpers.FormHelper;
import play.Logger;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.OnSubmit;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;

import java.util.List;

public class BasicPageAdminProvider {

    private static final String PAGE_TYPE_PREFIX = "origo.admin.basicpage";
    private static final String LIST_TYPE = PAGE_TYPE_PREFIX + ".list";
    private static final String EDIT_TYPE = PAGE_TYPE_PREFIX + ".edit";
    private static final String SUBMIT_TYPE = PAGE_TYPE_PREFIX + ".submit";
    private static final String DASHBOARD_TYPE = PAGE_TYPE_PREFIX + ".dashboard";

    @Provides(type = Admin.DASHBOARD, with = DASHBOARD_TYPE)
    public static UIElement createDashboardItem() {

        String url = AdminHelper.getURLForAdminAction(PAGE_TYPE_PREFIX + ".list");

        UIElement linkElement = new UIElement(UIElement.ANCHOR, 10, "List").addAttribute("href", url).addAttribute("class", "dashboard item link");
        UIElement dashboardElement = new UIElement(Admin.DASHBOARD, 10, "Pages").addChild(linkElement);

        return dashboardElement;
    }

    @Provides(type = Provides.NODE, with = LIST_TYPE)
    public static Node createListPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("List Basic Pages");
        page.rootNode = rootNode;
        return page;
    }

    //@Admin.Page(name = "list")
    @OnLoad(type = Provides.NODE, with = LIST_TYPE)
    public static void createListPage(Node node) {
        List<BasicPage> basicPages = BasicPage.findAllLatestVersions();

        UIElement panelElement = new UIElement(UIElement.PANEL, 10).addAttribute("class", "panel pages");
        for (BasicPage page : basicPages) {
            String editURL = AdminHelper.getURLForAdminAction(EDIT_TYPE, page.getNodeId());
            UIElement element = new UIElement(UIElement.ANCHOR, 10, page.getTitle()).addAttribute("href", editURL);
            panelElement.addChild(element);
        }
        node.addUIElement(panelElement);
    }

    @Provides(type = Provides.NODE, with = EDIT_TYPE)
    public static Node createEditPage(RootNode rootNode) {
        AdminPage page = new AdminPage(rootNode.nodeId);
        page.setTitle("Edit Basic Page");
        page.rootNode = rootNode;
        return page;
    }

    //@Admin.Page(name = "edit")
    @OnLoad(type = Provides.NODE, with = EDIT_TYPE)
    public static void createEditPage(Node node) {
        BasicPage basicPage = BasicPage.findLatestVersion(node.getNodeId());
        if (basicPage == null) {
            node.addUIElement(new UIElement(UIElement.TEXT, 10, "Page '" + node.getNodeId() + "' does not exist."));
            return;
        }

        Content bodyContent = Content.findWithIdentifier(basicPage.getBodyReferenceId());
        Content leadContent = Content.findWithIdentifier(basicPage.getLeadReferenceId());

        UIElement formElement = FormHelper.createDefaultFormElement(node, SUBMIT_TYPE).setId("basicpageform").addAttribute("class", "origo-basicpageform, form");

        UIElement titleElement = new UIElement(UIElement.PANEL, 10).addAttribute("class", "field");
        titleElement.addChild(new UIElement(UIElement.LABEL, 10, "Title").addAttribute("for", "origo-basicpageform-title"));
        titleElement.addChild(new UIElement(UIElement.INPUT_TEXT, 20).addAttribute("name", "origo-basicpageform-title").addAttribute("value", basicPage.getTitle()));
        formElement.addChild(titleElement);

        UIElement leadElement = new UIElement(UIElement.PANEL, 20).addAttribute("class", "field");
        leadElement.addChild(new UIElement(UIElement.LABEL, 10, "Lead").addAttribute("for", "origo-basicpageform-lead"));
        leadElement.addChild(AdminHelper.createRichTextEditor(node, leadContent).setWeight(20).addAttribute("class", "editor richtext").
                addAttribute("name", "origo-basicpageform-lead").addAttribute("cols", "80").addAttribute("rows", "10"));
        formElement.addChild(leadElement);

        UIElement bodyElement = new UIElement(UIElement.PANEL, 30).addAttribute("class", "field");
        bodyElement.addChild(new UIElement(UIElement.LABEL, 10, "Body").addAttribute("for", "origo-basicpageform-body"));
        bodyElement.addChild(AdminHelper.createRichTextEditor(node, bodyContent).setWeight(20).addAttribute("class", "editor richtext").
                addAttribute("name", "origo-basicpageform-body").addAttribute("cols", "80").addAttribute("rows", "20"));
        formElement.addChild(bodyElement);

        UIElement actionPanel = new UIElement(UIElement.PANEL, 40).addAttribute("class", "field");
        actionPanel.addChild(new UIElement(UIElement.INPUT_BUTTON, 10, "Save").addAttribute("type", "submit"));
        formElement.addChild(actionPanel);

        node.addUIElement(formElement);
    }

    @OnSubmit(with = SUBMIT_TYPE)
    public static void storePage() {

        /*
        String nodeId = DefaultFormProvider.getNodeId(params);
        RootNode rootNode = RootNode.findLatestVersionWithNodeId(nodeId);
        if (rootNode == null) {
            throw new RuntimeException("Root node with id=\'" + nodeId + "\' does not exist");
        }

        Node node = NodeHelper.triggerProvidesNodeListener(rootNode.type, rootNode);
        */


        //String title = params.get("origo-basicpageform-title");
        //Logger.info("Title = " + title);
        //page.save()
        Logger.info("Bla bla");
    }

}

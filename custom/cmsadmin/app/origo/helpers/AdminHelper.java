package origo.helpers;

import controllers.origo.admin.Application;
import models.origo.core.Content;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Router;

import java.util.HashMap;
import java.util.Map;

public class AdminHelper {

    public static String getURLForAdminAction(String type) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("type", type);
        Router.ActionDefinition actionDefinition = Router.reverse(Application.class.getName() + ".pageWithType", args);
        if (actionDefinition != null) {
            return actionDefinition.url;
        }
        return null;
    }

    public static String getURLForAdminAction(String type, String identifier) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("type", type);
        args.put("identifier", identifier);
        Router.ActionDefinition actionDefinition = Router.reverse(Application.class.getName() + ".pageWithTypeAndIdentifier", args);
        if (actionDefinition != null) {
            return actionDefinition.url;
        }
        return null;
    }

    public static UIElement createRichTextEditor(Node node, Content content) {
        String editorType = SettingsHelper.Admin.getEditorType();
        EditorHelper.triggerBeforeRichTextEditorLoaded(editorType, node, content);
        UIElement richTextEditor = EditorHelper.triggerProvidesRichTextEditorListener(editorType, node, content);
        EditorHelper.triggerAfterRichTextEditorLoaded(editorType, node, richTextEditor, content);
        return richTextEditor;
    }

    public static UIElement createDashboardItem(String dashboardItemName, Node node) {
        DashboardHelper.triggerBeforeDashboardItemLoaded(dashboardItemName, node);
        UIElement uiElement = DashboardHelper.triggerProvidesDashboardItemListener(dashboardItemName, node);
        DashboardHelper.triggerAfterDashboardItemLoaded(dashboardItemName, node);
        return uiElement;
    }
}

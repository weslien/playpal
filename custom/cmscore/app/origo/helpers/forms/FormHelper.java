package origo.helpers.forms;

import controllers.origo.core.SubmitController;
import origo.helpers.SettingsHelper;
import origo.helpers.URLHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Scope;

import java.util.HashMap;
import java.util.Map;

public class FormHelper {

    private static final String NODE_ID = "_core_node_id";
    private static final String NODE_VERSION = "_core_node_version";

    public static UIElement createFormElement(Node node, String withType) {
        return createFormElement(SettingsHelper.Core.getDefaultFormProviderType(), node, withType);
    }

    public static UIElement createFormElement(String formProviderType, Node node, String withType) {
        OnLoadFormHelper.triggerBeforeListener(withType, node);
        UIElement formElement = ProvidesFormHelper.triggerListener(formProviderType, node, String.class, withType);
        addNodeIdAndVersion(formElement, node);
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(UIElement.class, formElement);
        args.put(String.class, withType);
        OnLoadFormHelper.triggerAfterListener(withType, node, args);
        return formElement;
    }

    public static String getPostURL() {
        return URLHelper.getReverseURL(SubmitController.class, "submit");
    }

    public static String getNodeIdParamName() {
        return NODE_ID;
    }

    public static String getNodeVersionParamName() {
        return NODE_VERSION;
    }

    public static String getNodeId(Scope.Params params) {
        return params.get(NODE_ID);
    }

    public static Long getNodeVersion(Scope.Params params) {
        try {
            return Long.parseLong(params.get(NODE_VERSION));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Version is not a number: " + e.getLocalizedMessage(), e);
        }
    }

    public static void addNodeIdAndVersion(UIElement form, Node node) {
        form.
                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", FormHelper.getNodeIdParamName()).addAttribute("value", node.getNodeId())).
                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", FormHelper.getNodeVersionParamName()).addAttribute("value", String.valueOf(node.getVersion())));
    }
}

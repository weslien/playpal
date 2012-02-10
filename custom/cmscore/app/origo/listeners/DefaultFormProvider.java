package origo.listeners;

import origo.helpers.FormHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Scope;

/**
 * Default implementation for a form provider, a different form provider can be used at any time by creating a
 * different form (see FormHelper). A default form provider is set up in the settings but can be changed to any
 * other provider.
 *
 * @see FormHelper
 */
public class DefaultFormProvider {

    public static final String TYPE = "origo.core.basicform";
    private static final String NODE_ID = "_core_node_id";
    private static final String NODE_VERSION = "_core_node_version";

    @Provides(type = Provides.FORM, with = TYPE)
    public static UIElement createBasicForm(Node node) {
        return new UIElement(UIElement.FORM).
                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", NODE_ID).addAttribute("value", node.getNodeId())).
                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", NODE_VERSION).addAttribute("value", String.valueOf(node.getVersion()))).
                addAttribute("action", FormHelper.getPostURL()).
                addAttribute("method", "POST");
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
}

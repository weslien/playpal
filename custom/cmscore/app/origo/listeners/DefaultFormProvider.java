package origo.listeners;

import origo.helpers.forms.FormHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.forms.ProvidesForm;
import play.modules.origo.core.ui.UIElement;

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

    @ProvidesForm(with = TYPE)
    public static UIElement createBasicForm(Node node) {
        return new UIElement(UIElement.FORM).
                addAttribute("action", FormHelper.getPostURL()).
                addAttribute("method", "POST");
    }

}

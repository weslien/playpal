package origo.listeners;

import origo.helpers.FormHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Scope;

public class DefaultFormProvider {

    public static final String TYPE = "origo.core.basicform";
    private static final String NODE_ID = "_core_node_id";

    @Provides(type = Provides.FORM, with = TYPE)
    public static UIElement createBasicForm(Node node) {
        return new UIElement(UIElement.FORM).
                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", NODE_ID).addAttribute("value", node.getNodeId())).
                addAttribute("action", FormHelper.getPostURL()).
                addAttribute("method", "POST");
    }

    public static String getNodeId(Scope.Params params) {
        return params.get(NODE_ID);
    }

}

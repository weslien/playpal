package origo.listeners;

import controllers.origo.core.BasicPostController;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Router;

public class BasicFormProvider {

    public static final String TYPE = "origo.core.basicform";

    @Provides(type = Provides.FORM, with = TYPE)
    public static UIElement createBasicForm(Node node) {
        return new UIElement(UIElement.FORM).

                addAttribute("action", getPostURL()).
                addAttribute("method", "POST").

                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", "_core_form_type").addAttribute("value", TYPE)).
                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", "_core_node_id").addAttribute("value", node.getNodeId()));
    }

    public static String getPostURL() {
        Router.ActionDefinition actionDefinition = Router.reverse(BasicPostController.class.getName() + ".submit");
        if (actionDefinition != null) {
            return actionDefinition.url;
        }
        return null;
    }
}

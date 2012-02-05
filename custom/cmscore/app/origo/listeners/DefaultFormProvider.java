package origo.listeners;

import origo.helpers.FormHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;

public class DefaultFormProvider {

    public static final String TYPE = "origo.core.basicform";

    @Provides(type = Provides.FORM, with = TYPE)
    public static UIElement createBasicForm(Node node) {
        return FormHelper.
                addTypeAndNodeIdFields(new UIElement(UIElement.FORM), TYPE, node.getNodeId()).

                addAttribute("action", FormHelper.getPostURL()).
                addAttribute("method", "POST");
    }

}

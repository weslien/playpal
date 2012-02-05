package origo.helpers;

import controllers.origo.core.PostController;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Router;
import play.mvc.Scope;

public class FormHelper {

    public static final String NODE_ID = "_core_form_id";
    public static final String FORM_TYPE = "_core_form_type";

    public static UIElement createDefaultFormElement(Node node) {
        return createFormElement(node, SettingsHelper.Core.getDefaultFormType());
    }

    public static UIElement createFormElement(Node node, String withType) {
        OnLoadHelper.triggerBeforeListener(OnLoad.FORM, withType, node);
        UIElement formElement = ProvidesHelper.triggerListener(Provides.FORM, withType, node);
        OnLoadHelper.triggerBeforeListener(OnLoad.FORM, withType, node, UIElement.class, formElement);
        return formElement;
    }

    public static UIElement addTypeAndNodeIdFields(UIElement uiElement, String type, String id) {
        if (!UIElement.FORM.equals(uiElement.getType())) {
            throw new RuntimeException("FormHelper.addTypeAndIdFields only work for UIElement with type=FORM");
        }
        return uiElement.addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", FORM_TYPE).addAttribute("value", type)).
                addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", NODE_ID).addAttribute("value", id));
    }

    public static String getFormTypeFromForm(Scope.Params params) {
        return params.get(FORM_TYPE);
    }

    public static String getNodeIdFromForm(Scope.Params params) {
        return params.get(NODE_ID);
    }

    public static String getPostURL() {
        Router.ActionDefinition actionDefinition = Router.reverse(PostController.class.getName() + ".submit");
        if (actionDefinition != null) {
            return actionDefinition.url;
        }
        return null;
    }
}

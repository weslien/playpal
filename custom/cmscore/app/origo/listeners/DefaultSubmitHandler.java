package origo.listeners;

import controllers.origo.core.SubmitController;
import origo.helpers.forms.OnSubmitHelper;
import origo.helpers.forms.SubmitStateHelper;
import play.Logger;
import play.modules.origo.core.annotations.forms.OnLoadForm;
import play.modules.origo.core.annotations.forms.SubmitState;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Scope;

/**
 * Default implementation of the submit handler. Alternate submit handlers can be used by changing the settings.
 * This submit handler is based on a 'type' which is added to the form using an \@OnLoad hook by this handler.
 */
public class DefaultSubmitHandler {

    private static final String WITH_TYPE = "_core_with_type";

    @play.modules.origo.core.annotations.forms.SubmitHandler
    public static void handleSubmit(Scope.Params params) {

        String withType = getWithType(params);
        if (withType == null) {
            Logger.error("DefaultSubmitHandler requires a request parameter  named \'" + WITH_TYPE + "\' to be present in the request");
        }

        // TODO: insert validation here

        OnSubmitHelper.triggerListeners(withType, Scope.Params.class, params);

        SubmitStateHelper.triggerListener(SubmitState.SUCCESS, withType, Scope.Params.class, params);
    }

    @OnLoadForm
    public static void addWithTypeField(UIElement uiElement, String withType) {
        if (DefaultSubmitHandler.class.isAssignableFrom(SubmitController.getActiveSubmitHandler())) {
            uiElement.addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", WITH_TYPE).addAttribute("value", withType));
        }
    }

    public static String getWithType(Scope.Params params) {
        return params.get(WITH_TYPE);
    }

}

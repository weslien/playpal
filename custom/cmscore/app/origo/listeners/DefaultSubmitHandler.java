package origo.listeners;

import controllers.origo.core.SubmitController;
import origo.helpers.OnSubmitHelper;
import play.Logger;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.SubmitHandler;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Scope;

public class DefaultSubmitHandler {

    private static final String WITH_TYPE = "_core_with_type";

    @SubmitHandler
    public static void handleSubmit(Scope.Params params) {

        String withType = getWithType(params);
        if (withType == null) {
            Logger.error("DefaultSubmitHandler requires a request parameter  named \'" + WITH_TYPE + "\' to be present in the request");
        }

        OnSubmitHelper.triggerListeners(withType, Scope.Params.class, params);

    }

    @OnLoad(type = UIElement.FORM)
    public static void addWithTypeField(UIElement uiElement, String withType) {
        if (DefaultSubmitHandler.class.isAssignableFrom(SubmitController.getActivePostHandler())) {
            uiElement.addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", WITH_TYPE).addAttribute("value", withType));
        }
    }

    public static String getWithType(Scope.Params params) {
        return params.get(WITH_TYPE);
    }

}

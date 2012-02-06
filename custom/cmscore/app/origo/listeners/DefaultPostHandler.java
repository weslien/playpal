package origo.listeners;

import controllers.origo.core.PostController;
import origo.helpers.OnPostHelper;
import play.Logger;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.PostHandler;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Scope;

public class DefaultPostHandler {

    private static final String WITH_TYPE = "_core_with_type";

    @PostHandler
    public static void handlePost(Scope.Params params) {

        String withType = getWithType(params);
        if (withType == null) {
            Logger.error("DefaultPostHandler requires a request parameter  named \'" + WITH_TYPE + "\' to be present in the request");
        }

        OnPostHelper.triggerListeners(withType, Scope.Params.class, params);

    }

    @OnLoad(type = UIElement.FORM)
    public static void addWithTypeField(UIElement uiElement, String withType) {
        if (DefaultPostHandler.class.isAssignableFrom(PostController.getActivePostHandler())) {
            uiElement.addChild(new UIElement(UIElement.INPUT_HIDDEN).addAttribute("name", WITH_TYPE).addAttribute("value", withType));
        }
    }

    public static String getWithType(Scope.Params params) {
        return params.get(WITH_TYPE);
    }

}

package origo.helpers;

import controllers.origo.core.PostController;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Router;

import java.util.HashMap;
import java.util.Map;

public class FormHelper {

    public static UIElement createDefaultFormElement(Node node, String withType) {
        return createFormElement(SettingsHelper.Core.getDefaultFormProviderType(), node, withType);
    }

    public static UIElement createFormElement(String formProviderType, Node node, String withType) {
        OnLoadHelper.triggerBeforeListener(OnLoad.FORM, formProviderType, node);
        UIElement formElement = ProvidesHelper.triggerListener(Provides.FORM, formProviderType, node, String.class, withType);
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(UIElement.class, formElement);
        args.put(String.class, withType);
        OnLoadHelper.triggerAfterListener(OnLoad.FORM, formProviderType, node, args);
        return formElement;
    }

    public static String getPostURL() {
        Router.ActionDefinition actionDefinition = Router.reverse(PostController.class.getName() + ".submit");
        if (actionDefinition != null) {
            return actionDefinition.url;
        }
        return null;
    }
}

package origo.helpers;

import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;

public class FormHelper {

    public static UIElement createDefaultFormElement(Node node) {
        return createFormElement(node, SettingsHelper.Core.getDefaultFormType());
    }

    public static UIElement createFormElement(Node node, String withType) {
        OnLoadHelper.triggerBeforeListener(OnLoad.FORM, withType, node);
        UIElement formElement = ProvidesHelper.triggerListener(Provides.FORM, withType, node);
        OnLoadHelper.triggerBeforeListener(OnLoad.FORM, withType, node, UIElement.class, formElement);
        return formElement;
    }


}

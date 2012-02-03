package origo.helpers;

import models.origo.core.Content;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;

import java.util.HashMap;
import java.util.Map;

public class EditorHelper {

    /*
    * Convenience methods for hooks with RICHTEXT_EDITOR type
    */
    public static UIElement triggerProvidesRichTextEditorListener(String withType, Node node, Content content) {
        return ProvidesHelper.triggerListener(Admin.RICHTEXT_EDITOR, withType, node, Content.class, content);
    }

    public static void triggerBeforeRichTextEditorLoaded(String withType, Node node, Content content) {
        OnLoadHelper.triggerBeforeListener(Admin.RICHTEXT_EDITOR, withType, node, Content.class, content);
    }

    public static void triggerAfterRichTextEditorLoaded(String withType, Node node, UIElement uiElement, Content content) {
        Map<Class, Object> args = new HashMap<Class, Object>();
        args.put(Content.class, content);
        args.put(UIElement.class, uiElement);
        OnLoadHelper.triggerAfterListener(Admin.RICHTEXT_EDITOR, withType, node, args);
    }


}

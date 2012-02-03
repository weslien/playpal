package origo.listeners;

import models.origo.core.Content;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;
import play.mvc.Router;

public class TinyMCEEditorProvider {

    public static final String EDITOR_TYPE = "origo.admin.editor.tinymce";

    @Provides(type = Admin.RICHTEXT_EDITOR, with = EDITOR_TYPE)
    public static UIElement createDashboardItem(Node node) {

        String script = StaticRouter.getStaticURL("/public/javascripts/tiny_mce/tiny_mce.js");
        if (script != null) {
            node.addHeadUIElement(new UIElement(UIElement.SCRIPT, 10).addAttribute("type", "text/javascript").addAttribute("src", script));
            node.addHeadUIElement(new UIElement(UIElement.SCRIPT, 20, "tinyMCE.init({ mode:\"textareas\" });</script>").addAttribute("type", "text/javascript"));
        }

        return new UIElement(UIElement.INPUT_TEXTAREA);
    }

    @OnLoad(type = Admin.RICHTEXT_EDITOR, with = EDITOR_TYPE)
    public static void addContent(Node node, UIElement uiElement, Content content) {
        uiElement.setBody(content.value);
    }

    public static class StaticRouter extends Router {
        public static String getStaticURL(String file) {
            return Router.getBaseUrl() + file;
        }
    }

}

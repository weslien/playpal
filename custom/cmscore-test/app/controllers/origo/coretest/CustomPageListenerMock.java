package controllers.origo.coretest;

import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.UIElement;

import java.util.Collection;

public class CustomPageListenerMock {

    /**
     * @param node the node to modify
     */
    @OnLoad(type = OnLoad.NODE, with = "models.origo.core.BasicPage")
    public static void setupTestData(Node node) {

        node.addHeadUIElement(new UIElement(UIElement.META, 10).addAttribute("http-equiv", "Content-Type").addAttribute("content", "text/html; charset=utf-8"));
        node.addHeadUIElement(new UIElement(UIElement.STYLE, 10, "\n\tbody {\n\t\tbackground-color: #ff0000;\n}").addAttribute("type", "text/css"));
        node.addHeadUIElement(new UIElement(UIElement.STYLE, 10).addAttribute("type", "text/css").addAttribute("src", "test/css/style_head.css"));

        node.addUIElement(new UIElement(UIElement.STYLE, 10).addAttribute("type", "text/css").addAttribute("src", "test/css/style_inline.css"));
        node.addUIElement(new UIElement(UIElement.PANEL, 10).addChild(new UIElement(UIElement.TEXT, 12, "Bla bla")));
    }

    @OnLoad(type = OnLoad.NAVIGATION)
    public static void setupTestNavigation(Node node, Collection<NavigationElement> navigationElements, String section) {
        navigationElements.add(new NavigationElement(section, "Programmatically Added", "http://google.com"));
    }

}

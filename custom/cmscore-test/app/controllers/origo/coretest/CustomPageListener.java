package controllers.origo.coretest;

import models.origo.core.Page;
import play.modules.origo.core.Node;

import java.util.Collection;

public class CustomPageListener {

    /**
     * TODO: Just for testing, remove eventually
     *
     * @param node the node to modify
     */
    @play.modules.origo.core.annotations.OnLoad(type = play.modules.origo.core.annotations.OnLoad.Type.NODE, with = Page.class)
    public static void setupTestData(Node node) {

        play.modules.origo.core.ui.UIElement metaElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.META, 10);
        metaElement.getAttributes().put("http-equiv", "Content-Type");
        metaElement.getAttributes().put("content", "text/html; charset=utf-8");
        node.addUIElement(metaElement);

        play.modules.origo.core.ui.UIElement styleElementWithContent = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.STYLE, 10, "\n\tbody {\n\t\tbackground-color: #ff0000;\n}");
        styleElementWithContent.put("type", "text/css");
        node.addUIElement(styleElementWithContent);

        play.modules.origo.core.ui.UIElement styleElementWithSrc = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.STYLE, 10);
        styleElementWithSrc.put("type", "text/css");
        styleElementWithSrc.put("src", "test/css/style_head.css");
        node.addUIElement("head", styleElementWithSrc);

        play.modules.origo.core.ui.UIElement inlineStyleElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.STYLE, 10);
        inlineStyleElement.put("type", "text/css");
        inlineStyleElement.put("src", "test/css/style_inline.css");
        node.addUIElement("main", inlineStyleElement);

        play.modules.origo.core.ui.UIElement panel = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.PANEL, 10);
        node.addUIElement("left", panel);

        panel.addChild(new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.TEXT, 12, "Bla bla"));

    }

    @play.modules.origo.core.annotations.OnLoad(type = play.modules.origo.core.annotations.OnLoad.Type.NAVIGATION)
    public static void setupTestNavigation(Node node, Collection<play.modules.origo.core.ui.NavigationElement> navigationElements, String section) {
        navigationElements.add(new play.modules.origo.core.ui.NavigationElement(section, "Programmatically Added", "http://google.com"));
    }

}

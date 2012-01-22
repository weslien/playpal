package controllers.cmscoretest;

import models.cmscore.Page;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.OnLoad;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.UIElement;

import java.util.Collection;

public class CmsCorePageListenerMock {

    @OnLoad(type = OnLoad.Type.NODE, with = Page.class)
    public static void setupTestData(Node node) {

        UIElement metaElement = new UIElement(UIElementType.META, 10);
        metaElement.getAttributes().put("http-equiv", "Content-Type");
        metaElement.getAttributes().put("content", "text/html; charset=utf-8");
        node.addUIElement(metaElement);

        UIElement styleElementWithContent = new UIElement(UIElementType.STYLE, 10, "\n\tbody {\n\t\tbackground-color: #ff0000;\n}");
        styleElementWithContent.put("type", "text/css");
        node.addUIElement(styleElementWithContent);

        UIElement styleElementWithSrc = new UIElement(UIElementType.STYLE, 10);
        styleElementWithSrc.put("type", "text/css");
        styleElementWithSrc.put("src", "test/css/style_head.css");
        node.addUIElement("head", styleElementWithSrc);

        UIElement inlineStyleElement = new UIElement(UIElementType.STYLE, 10);
        inlineStyleElement.put("type", "text/css");
        inlineStyleElement.put("src", "test/css/style_inline.css");
        node.addUIElement("main", inlineStyleElement);

        UIElement panel = new UIElement(UIElementType.PANEL, 10);
        node.addUIElement("left", panel);

        panel.addChild(new UIElement(UIElementType.TEXT, 12, "Bla bla"));

    }

    @OnLoad(type = OnLoad.Type.NAVIGATION)
    public static void setupTestNavigation(Node node, Collection<NavigationElement> navigationElements, String section) {
        navigationElements.add(new NavigationElement(section, "Programmatically Added", "http://google.com"));
    }


}

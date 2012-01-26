package controllers.origo.contactform;

import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.UIElementType;
import play.modules.origo.core.ui.UIElement;

import java.util.Collections;

public class ContactFormProvider {

    @OnLoad(type = OnLoad.TYPE_NODE, with = "models.origo.core.BasicPage")
    public static void createForm(Node node) {

        // TODO: Statically linked to page 2 - Page Not Found - for now
        if (node.getNodeId().equals("c9615819-0556-4e70-b6a9-a66c5b8d4c1a")) {

            UIElement formElement = new UIElement("contactform", UIElementType.FORM, Collections.singletonMap("class", "origo-contactform, form"));

            UIElement namePanelElement = new UIElement(UIElementType.PANEL, 10);
            namePanelElement.addChild(new UIElement(UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-name"), 10, "Name"));
            namePanelElement.addChild(new UIElement(UIElementType.INPUT_TEXT, Collections.singletonMap("name", "origo-contactform-name"), 20));
            formElement.addChild(namePanelElement);

            UIElement emailPanelElement = new UIElement(UIElementType.PANEL, 20);
            emailPanelElement.addChild(new UIElement(UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-email"), 10, "Email"));
            emailPanelElement.addChild(new UIElement(UIElementType.INPUT_TEXT, Collections.singletonMap("name", "origo-contactform-email"), 20));
            formElement.addChild(emailPanelElement);

            UIElement textPanelElement = new UIElement(UIElementType.PANEL, 30);
            textPanelElement.addChild(new UIElement(UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-text"), 10, "Question/Comment"));
            textPanelElement.addChild(new UIElement(UIElementType.INPUT_TEXTAREA, Collections.singletonMap("name", "origo-contactform-text"), 20));
            formElement.addChild(textPanelElement);

            UIElement buttonPanelElement = new UIElement(UIElementType.PANEL, 40);
            buttonPanelElement.addChild(new UIElement(UIElementType.INPUT_BUTTON, Collections.singletonMap("name", "origo-contactform-button"), 20, "Send"));
            formElement.addChild(buttonPanelElement);

            node.addUIElement(formElement);
        }

    }

}

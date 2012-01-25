package controllers.origo.contactform;

import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.UIElementType;
import play.modules.origo.core.ui.UIElement;

import java.util.Collections;

public class ContactFormProvider {

    @OnLoad(type = OnLoad.Type.NODE, with = "origo.contactform.ContactForm")
    public static UIElement createForm(Node node) {

        // TODO: Statically linked to page 2 - Page Not Found - for now
        if (node.getNodeId().equals("c9615819-0556-4e70-b6a9-a66c5b8d4c1a")) {

            UIElement formElement = new UIElement("contactform", UIElementType.FORM, Collections.singletonMap("class", "origo-contactform, form"));

            UIElement namePanelElement = new UIElement(UIElementType.PANEL, 10);
            UIElement nameLabelElement = new UIElement(UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-name"), 10);
            namePanelElement.addChild(nameLabelElement);
            UIElement nameInputElement = new UIElement(UIElementType.INPUT_TEXT, Collections.singletonMap("name", "origo-contactform-name"), 20);
            namePanelElement.addChild(nameInputElement);
            formElement.addChild(namePanelElement);

            UIElement emailPanelElement = new UIElement(UIElementType.PANEL, 20);
            UIElement emailLabelElement = new UIElement(UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-email"), 10);
            namePanelElement.addChild(emailLabelElement);
            UIElement emailInputElement = new UIElement(UIElementType.INPUT_TEXT, Collections.singletonMap("name", "origo-contactform-email"), 20);
            namePanelElement.addChild(emailInputElement);
            formElement.addChild(emailPanelElement);

            UIElement textPanelElement = new UIElement(UIElementType.PANEL, 30);
            UIElement textLabelElement = new UIElement(UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-text"), 10);
            namePanelElement.addChild(textLabelElement);
            UIElement textInputElement = new UIElement(UIElementType.INPUT_TEXTAREA, Collections.singletonMap("name", "origo-contactform-text"), 20);
            textPanelElement.addChild(textInputElement);
            formElement.addChild(textPanelElement);

            UIElement buttonPanelElement = new UIElement(UIElementType.PANEL, 40);
            UIElement buttonInputElement = new UIElement(UIElementType.INPUT_BUTTON, Collections.singletonMap("name", "origo-contactform-button"), 20);
            namePanelElement.addChild(buttonInputElement);
            formElement.addChild(buttonPanelElement);

            return formElement;
        }

        return null;
    }

}

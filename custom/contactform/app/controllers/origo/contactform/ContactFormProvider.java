package controllers.origo.contactform;

import java.util.Collections;

public class ContactFormProvider {

    @play.modules.origo.core.annotations.Provides(type = play.modules.origo.core.annotations.Provides.Type.SEGMENT, with = "origo.contactform.ContactForm")
    public static play.modules.origo.core.ui.UIElement createForm() {

        play.modules.origo.core.ui.UIElement formElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.FORM, Collections.singletonMap("class", "origo-contactform, form"), 30);

        play.modules.origo.core.ui.UIElement namePanelElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.PANEL, 10);
        play.modules.origo.core.ui.UIElement nameLabelElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-name"), 10);
        namePanelElement.addChild(nameLabelElement);
        play.modules.origo.core.ui.UIElement nameInputElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.INPUT_TEXT, Collections.singletonMap("name", "origo-contactform-name"), 20);
        namePanelElement.addChild(nameInputElement);
        formElement.addChild(namePanelElement);

        play.modules.origo.core.ui.UIElement emailPanelElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.PANEL, 20);
        play.modules.origo.core.ui.UIElement emailLabelElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-email"), 10);
        namePanelElement.addChild(emailLabelElement);
        play.modules.origo.core.ui.UIElement emailInputElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.INPUT_TEXT, Collections.singletonMap("name", "origo-contactform-email"), 20);
        namePanelElement.addChild(emailInputElement);
        formElement.addChild(emailPanelElement);

        play.modules.origo.core.ui.UIElement textPanelElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.PANEL, 30);
        play.modules.origo.core.ui.UIElement textLabelElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.LABEL, Collections.singletonMap("for", "origo-contactform-text"), 10);
        namePanelElement.addChild(textLabelElement);
        play.modules.origo.core.ui.UIElement textInputElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.INPUT_TEXTAREA, Collections.singletonMap("name", "origo-contactform-text"), 20);
        namePanelElement.addChild(textInputElement);

        play.modules.origo.core.ui.UIElement buttonPanelElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.PANEL, 40);
        play.modules.origo.core.ui.UIElement buttonInputElement = new play.modules.origo.core.ui.UIElement(play.modules.origo.core.annotations.UIElementType.INPUT_BUTTON, Collections.singletonMap("name", "origo-contactform-button"), 20);
        namePanelElement.addChild(buttonInputElement);
        formElement.addChild(buttonPanelElement);


        return formElement;
    }

}

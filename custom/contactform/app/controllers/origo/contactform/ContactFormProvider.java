package controllers.origo.contactform;

import origo.helpers.forms.FormHelper;
import origo.listeners.SegmentProvider;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.annotations.forms.OnLoadForm;
import play.modules.origo.core.ui.UIElement;

public class ContactFormProvider {

    private static final String BASE_TYPE = "origo.contactform.ContactForm";

    @Provides(type = SegmentProvider.SEGMENT, with = BASE_TYPE)
    public static UIElement createSegment(Node node) {

        // TODO: Statically linked to page 2 - Page Not Found - for now
        if (node.getNodeId().equals("c9615819-0556-4e70-b6a9-a66c5b8d4c1a")) {
            return FormHelper.createFormElement(node, BASE_TYPE);
        }

        return null;
    }

    @OnLoadForm(with = BASE_TYPE)
    public static void loadForm(UIElement formElement) {

        formElement.addAttribute("class", "origo-contactform, form");

        UIElement namePanelElement = new UIElement(UIElement.PANEL, 10).addAttribute("class", "field");
        namePanelElement.addChild(new UIElement(UIElement.LABEL, 10, "Name").addAttribute("for", "origo-contactform-name"));
        namePanelElement.addChild(new UIElement(UIElement.INPUT_TEXT, 20).addAttribute("name", "origo-contactform-name"));
        formElement.addChild(namePanelElement);

        UIElement emailPanelElement = new UIElement(UIElement.PANEL, 20).addAttribute("class", "field");
        emailPanelElement.addChild(new UIElement(UIElement.LABEL, 10, "Email").addAttribute("for", "origo-contactform-email"));
        emailPanelElement.addChild(new UIElement(UIElement.INPUT_TEXT, 20).addAttribute("name", "origo-contactform-email"));
        formElement.addChild(emailPanelElement);

        UIElement textPanelElement = new UIElement(UIElement.PANEL, 30).addAttribute("class", "field");
        textPanelElement.addChild(new UIElement(UIElement.LABEL, 10, "Question/Comment").addAttribute("for", "origo-contactform-text"));
        textPanelElement.addChild(new UIElement(UIElement.INPUT_TEXTAREA, 20).addAttribute("name", "origo-contactform-text"));
        formElement.addChild(textPanelElement);

        UIElement buttonPanelElement = new UIElement(UIElement.PANEL, 40).addAttribute("class", "field");
        buttonPanelElement.addChild(new UIElement(UIElement.INPUT_BUTTON, 20, "Send").addAttribute("name", "origo-contactform-button"));
        formElement.addChild(buttonPanelElement);

    }

}

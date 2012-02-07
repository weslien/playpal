package origo.helpers;

import origo.fragments.FragmentLoader;
import play.modules.origo.core.ui.RenderingContext;
import play.modules.origo.core.ui.UIElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultDecorator {

    private static final String FRAGMENT_PREFIX = "origo.core.";

    public static String decorate(UIElement uiElement, RenderingContext renderingContext) {
        if (UIElement.META.equalsIgnoreCase(uiElement.getType())) {
            return decorateMeta(uiElement, renderingContext);
        } else if (UIElement.SCRIPT.equalsIgnoreCase(uiElement.getType())) {
            return decorateScript(uiElement, renderingContext);
        } else if (UIElement.STYLE.equalsIgnoreCase(uiElement.getType())) {
            return decorateStyle(uiElement, renderingContext);
        } else if (UIElement.LINK.equalsIgnoreCase(uiElement.getType())) {
            return decorateLink(uiElement, renderingContext);
        } else if (UIElement.FORM.equalsIgnoreCase(uiElement.getType())) {
            return decorateForm(uiElement, renderingContext);
        } else if (UIElement.INPUT_TEXT.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputText(uiElement, renderingContext);
        } else if (UIElement.LABEL.equalsIgnoreCase(uiElement.getType())) {
            return decorateLabel(uiElement, renderingContext);
        } else if (UIElement.INPUT_TEXTAREA.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputTextArea(uiElement, renderingContext);
        } else if (UIElement.INPUT_HIDDEN.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputHidden(uiElement, renderingContext);
        } else if (UIElement.INPUT_PASSWORD.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputPassword(uiElement, renderingContext);
        } else if (UIElement.INPUT_FILE.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputFile(uiElement, renderingContext);
        } else if (UIElement.INPUT_IMAGE.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputImage(uiElement, renderingContext);
        } else if (UIElement.INPUT_RADIO_BUTTON.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputRadioButton(uiElement, renderingContext);
        } else if (UIElement.INPUT_SELECT.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputSelect(uiElement, renderingContext);
        } else if (UIElement.INPUT_SELECT_OPTION.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputSelectOption(uiElement, renderingContext);
        } else if (UIElement.INPUT_BUTTON.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputButton(uiElement, renderingContext);
        } else if (UIElement.INPUT_SUBMIT.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputSubmit(uiElement, renderingContext);
        } else if (UIElement.INPUT_RESET.equalsIgnoreCase(uiElement.getType())) {
            return decorateInputReset(uiElement, renderingContext);
        } else if (UIElement.LIST_BULLET.equalsIgnoreCase(uiElement.getType())) {
            return decorateListBulleted(uiElement, renderingContext);
        } else if (UIElement.LIST_ORDERED.equalsIgnoreCase(uiElement.getType())) {
            return decorateListNumbered(uiElement, renderingContext);
        } else if (UIElement.LIST_ITEM.equalsIgnoreCase(uiElement.getType())) {
            return decorateListItem(uiElement, renderingContext);
        } else if (UIElement.ANCHOR.equalsIgnoreCase(uiElement.getType())) {
            return decorateAnchor(uiElement, renderingContext);
        } else if (UIElement.PANEL.equalsIgnoreCase(uiElement.getType())) {
            return decoratePanel(uiElement, renderingContext);
        } else if (UIElement.TEXT.equalsIgnoreCase(uiElement.getType())) {
            return decorateText(uiElement, renderingContext);
        } else if (UIElement.PARAGRAPH.equalsIgnoreCase(uiElement.getType())) {
            return decorateParagraph(uiElement, renderingContext);
        } else {
            return decorateUnknownType(uiElement, renderingContext);
        }
    }

    public static String decorateMeta(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "meta", uiElement, null);
    }

    public static String decorateLink(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "link", uiElement, null);
    }

    public static String decorateStyle(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "style", uiElement, uiElement.getBody());
    }

    public static String decorateScript(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "script", uiElement, uiElement.getBody());
    }

    public static String decorateForm(UIElement uiElement, RenderingContext renderingContext) {
        String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        return loadFragment(getFragmentPrefix(), "form", uiElement, body);
    }

    public static String decorateListBulleted(UIElement uiElement, RenderingContext renderingContext) {
        String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        return loadFragment(getFragmentPrefix(), "ul", uiElement, body);
    }

    public static String decorateListNumbered(UIElement uiElement, RenderingContext renderingContext) {
        String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        return loadFragment(getFragmentPrefix(), "ol", uiElement, body);
    }

    public static String decorateListItem(UIElement uiElement, RenderingContext renderingContext) {
        String body = uiElement.getBody();
        if (uiElement.hasChildren()) {
            body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        }
        return loadFragment(getFragmentPrefix(), "li", uiElement, body);
    }

    public static String decorateInputText(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "text"));
    }

    public static String decorateInputHidden(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "hidden"));
    }

    public static String decorateLabel(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "label", uiElement, uiElement.getBody());
    }

    public static String decorateInputTextArea(UIElement uiElement, RenderingContext renderingContext) {
        String body = uiElement.getBody();
        if (uiElement.hasChildren()) {
            body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        }
        return loadFragment(getFragmentPrefix(), "textarea", uiElement, body);
    }

    public static String decorateInputRadioButton(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "radiobutton"));
    }

    private static String decorateInputSelect(UIElement uiElement, RenderingContext renderingContext) {
        String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        return loadFragment(getFragmentPrefix(), "select", uiElement, body);
    }

    private static String decorateInputSelectOption(UIElement uiElement, RenderingContext renderingContext) {
        String body = uiElement.getBody();
        if (uiElement.hasChildren()) {
            body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        }
        return loadFragment(getFragmentPrefix(), "select_option", uiElement, body);
    }

    public static String decorateInputButton(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "button"));
    }

    public static String decorateInputSubmit(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "submit"));
    }

    public static String decorateInputReset(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "reset"));
    }

    public static String decorateInputImage(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "image"));
    }

    public static String decorateInputFile(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "file"));
    }

    public static String decorateInputPassword(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "password"));
    }

    public static String decoratePanel(UIElement uiElement, RenderingContext renderingContext) {
        String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        return loadFragment(getFragmentPrefix(), "panel", uiElement, body);
    }

    public static String decorateText(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "text", uiElement, uiElement.getBody(), Collections.<String, String>emptyMap());
    }

    public static String decorateParagraph(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "paragraph", uiElement, uiElement.getBody(), Collections.<String, String>emptyMap());
    }

    public static String decorateAnchor(UIElement uiElement, RenderingContext renderingContext) {
        String body = uiElement.getBody();
        if (uiElement.hasChildren()) {
            body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        }
        return loadFragment(getFragmentPrefix(), "anchor", uiElement, body);
    }

    public static String decorateUnknownType(UIElement uiElement, RenderingContext renderingContext) {
        String body = uiElement.getBody();
        if (uiElement.hasChildren()) {
            body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        }
        return loadFragment(getFragmentPrefix(), uiElement.getType(), uiElement, body, Collections.<String, String>emptyMap());
    }

    protected static String loadFragment(String prefix, String tagName, UIElement uiElement, String body) {
        return loadFragment(prefix, tagName, uiElement, body, Collections.<String, String>emptyMap());
    }

    protected static String loadFragment(String prefix, String tagName, UIElement uiElement, String body, Map<String, String> additionalAttributes) {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.putAll(additionalAttributes);
        attributes.putAll(uiElement.getAttributes());
        return FragmentLoader.loadHtmlFragment(prefix + tagName, uiElement, attributes, body);
    }

    public static String getFragmentPrefix() {
        return FRAGMENT_PREFIX;
    }
}

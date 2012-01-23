package helpers;

import controllers.cmscore.fragments.FragmentLoader;
import play.modules.cmscore.ui.RenderingContext;
import play.modules.cmscore.ui.UIElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultDecorator {

    private static final String FRAGMENT_PREFIX = "cmscore.";

    public static String decorate(UIElement uiElement, RenderingContext renderingContext) {
        switch (uiElement.getType()) {
            case META:
                return decorateMeta(uiElement, renderingContext);
            case SCRIPT:
                return decorateScript(uiElement, renderingContext);
            case STYLE:
                return decorateStyle(uiElement, renderingContext);
            case LINK:
                return decorateLink(uiElement, renderingContext);
            case FORM:
                return decorateForm(uiElement, renderingContext);
            case INPUT_TEXT:
                return decorateInputText(uiElement, renderingContext);
            case LABEL:
                return decorateLabel(uiElement, renderingContext);
            case INPUT_TEXTAREA:
                return decorateInputTextArea(uiElement, renderingContext);
            case INPUT_HIDDEN:
                return decorateInputHidden(uiElement, renderingContext);
            case INPUT_PASSWORD:
                return decorateInputPassword(uiElement, renderingContext);
            case INPUT_FILE:
                return decorateInputFile(uiElement, renderingContext);
            case INPUT_IMAGE:
                return decorateInputImage(uiElement, renderingContext);
            case INPUT_RADIO_BUTTON:
                return decorateInputRadioButton(uiElement, renderingContext);
            case INPUT_SELECT:
                return decorateInputSelect(uiElement, renderingContext);
            case INPUT_SELECT_OPTION:
                return decorateInputSelectOption(uiElement, renderingContext);
            case INPUT_BUTTON:
                return decorateInputButton(uiElement, renderingContext);
            case INPUT_SUBMIT:
                return decorateInputSubmit(uiElement, renderingContext);
            case INPUT_RESET:
                return decorateInputReset(uiElement, renderingContext);
            case LIST_BULLET:
                return decorateListBulleted(uiElement, renderingContext);
            case LIST_ORDERED:
                return decorateListNumbered(uiElement, renderingContext);
            case LIST_ITEM:
                return decorateListItem(uiElement, renderingContext);
            case PANEL:
                return decoratePanel(uiElement, renderingContext);
            case TEXT:
                return decorateText(uiElement, renderingContext);
            default:
                return null;
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
        if (uiElement.hasChildren()) {
            String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
            return loadFragment(getFragmentPrefix(), "li", uiElement, body);
        } else {
            return loadFragment(getFragmentPrefix(), "li", uiElement, uiElement.getBody());
        }
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
        if (uiElement.hasChildren()) {
            String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
            return loadFragment(getFragmentPrefix(), "textarea", uiElement, body);
        } else {
            return loadFragment(getFragmentPrefix(), "textarea", uiElement, uiElement.getBody());
        }
    }

    public static String decorateInputRadioButton(UIElement uiElement, RenderingContext renderingContext) {
        return loadFragment(getFragmentPrefix(), "input", uiElement, null, Collections.singletonMap("type", "radiobutton"));
    }

    private static String decorateInputSelect(UIElement uiElement, RenderingContext renderingContext) {
        String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
        return loadFragment(getFragmentPrefix(), "select", uiElement, body);
    }

    private static String decorateInputSelectOption(UIElement uiElement, RenderingContext renderingContext) {
        if (uiElement.hasChildren()) {
            String body = ThemeHelper.decorateChildren(uiElement, renderingContext);
            return loadFragment(getFragmentPrefix(), "select_option", uiElement, body);
        } else {
            return loadFragment(getFragmentPrefix(), "select_option", uiElement, uiElement.getBody());
        }
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

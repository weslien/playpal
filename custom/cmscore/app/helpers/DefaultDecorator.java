package helpers;

import play.modules.cmscore.RenderingContext;
import play.modules.cmscore.ui.UIElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultDecorator {

    public static String decorate(UIElement uiElement, RenderingContext renderingContext) {
        switch (uiElement.getType()) {
            case HEAD:
                return decorateHead(uiElement, renderingContext);
            case SCRIPT:
                return decorateScript(uiElement, renderingContext);
            case TITLE:
                return decorateTitle(uiElement, renderingContext);
            case META:
                return decorateMeta(uiElement, renderingContext);
            case BODY:
                return decorateBody(uiElement, renderingContext);
            case FORM:
                return decorateForm(uiElement, renderingContext);
            case INPUT_TEXT:
                return decorateInputText(uiElement, renderingContext);
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
            case INPUT_BUTTON:
                return decorateInputButton(uiElement, renderingContext);
            case INPUT_SUBMIT:
                return decorateInputSubmit(uiElement, renderingContext);
            case INPUT_RESET:
                return decorateInputReset(uiElement, renderingContext);
            default:
                return null;
        }
    }

    public static String decorateHead(UIElement uiElement, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder().append(writeStartTag("head", uiElement));
        sb.append(ThemeHelper.decorate(uiElement.getChildren(), renderingContext));
        sb.append(writeEndTag("head"));
        return sb.toString();
    }

    public static String decorateMeta(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("meta", uiElement);
    }

    public static String decorateScript(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("script", uiElement);
    }

    public static String decorateTitle(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("title", uiElement);
    }
    
    public static String decorateBody(UIElement uiElement, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder().append(writeStartTag("body", uiElement));
        renderingContext.nestle(uiElement);
        sb.append(ThemeHelper.decorate(uiElement.getChildren(), renderingContext));
        renderingContext.unNestle();
        sb.append(writeEndTag("body"));
        return sb.toString();
    }

    public static String decorateForm(UIElement uiElement, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder().append(writeStartTag("form", uiElement));
        renderingContext.nestle(uiElement);
        sb.append(ThemeHelper.decorate(uiElement.getChildren(), renderingContext));
        renderingContext.unNestle();
        sb.append(writeEndTag("form"));
        return sb.toString();
    }

    public static String decorateListBulleted(UIElement uiElement, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder().append(writeStartTag("ul", uiElement));
        renderingContext.nestle(uiElement);
        sb.append(ThemeHelper.decorate(uiElement.getChildren(), renderingContext));
        renderingContext.unNestle();
        sb.append(writeEndTag("ul"));
        return sb.toString();
    }
    
    public static String decorateListNumbered(UIElement uiElement, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder().append(writeStartTag("ol", uiElement));
        renderingContext.nestle(uiElement);
        sb.append(ThemeHelper.decorate(uiElement.getChildren(), renderingContext));
        renderingContext.unNestle();
        sb.append(writeEndTag("ol"));
        return sb.toString();
    }

    public static String decorateListItem(UIElement uiElement, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder().append(writeStartTag("li", uiElement));
        renderingContext.nestle(uiElement);
        sb.append(ThemeHelper.decorate(uiElement.getChildren(), renderingContext));
        sb.append(uiElement.getBody());
        sb.append(writeEndTag("ol"));
        renderingContext.unNestle();
        return sb.toString();
    }

    public static String decorateInputText(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "text"));
    }

    public static String decorateInputHidden(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "hidden"));
    }

    public static String decorateInputTextArea(UIElement uiElement, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder().append(writeStartTag("textarea", uiElement));
        sb.append(uiElement.getBody());
        sb.append(writeEndTag("textarea"));
        return sb.toString();
    }

    public static String decorateInputRadioButton(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "radiobutton"));
    }

    public static String decorateInputButton(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "button"));
    }

    public static String decorateInputSubmit(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "submit"));
    }

    public static String decorateInputReset(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "reset"));
    }

    public static String decorateInputImage(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "image"));
    }

    public static String decorateInputFile(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "file"));
    }

    public static String decorateInputPassword(UIElement uiElement, RenderingContext renderingContext) {
        return writeClosedTag("input", uiElement, Collections.singletonMap("type", "password"));
    }

    public static String writeClosedTag(String name, UIElement uiElement) {
        return writeClosedTag(name, uiElement, new HashMap<String, String>());
    }

    public static String writeClosedTag(String name, UIElement uiElement, Map<String, String> additionalAttributes) {
        StringBuilder sb = new StringBuilder("<").append(name);
        sb.append(writeAttribute("id", uiElement.getId()));
        sb.append(writeAttributes(uiElement.getAttributes()));
        sb.append(writeAttributes(additionalAttributes));
        return sb.append("/>").toString();
    }
    
    public static String writeStartTag(String name, UIElement uiElement) {
        return writeStartTag(name, uiElement, new HashMap<String, String>());
    }
    
    public static String writeStartTag(String name, UIElement uiElement, Map<String, String> additionalAttributes) {
        StringBuilder sb = new StringBuilder("<").append(name);
        sb.append(writeAttribute("id", uiElement.getId()));
        sb.append(writeAttributes(uiElement.getAttributes()));
        sb.append(writeAttributes(additionalAttributes));
        return sb.append(">").toString();
    }

    public static String writeAttributes(Map<String, String> additionalAttributes) {
        StringBuilder sb = new StringBuilder();
        for (String key : additionalAttributes.keySet()) {
            sb.append(writeAttribute(key, additionalAttributes.get(key)));
        }
        return sb.toString();
    }

    public static String writeEndTag(String name) {
        return new StringBuilder().append("</").append(name).append(">").toString();
    }
    
    public static String writeAttribute(String name, String value) {
        return new StringBuilder().append(" ").append(name).append("=\"").append(value).append("\"").toString();
    }

}

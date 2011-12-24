package helpers;

import play.modules.cmscore.*;
import play.modules.cmscore.annotations.CachedDecorator;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.UIElement;

import java.util.List;
import java.util.Map;

public class ThemeHelper {

    public static RenderedLeaf decorate(Leaf leaf) {
        RenderedLeaf renderedLeaf = new RenderedLeaf();
        CachedThemeVariant themeVariant = Themes.getThemeVariant(leaf.getThemeVariant());
        //TODO: Add a fallback for leaves without a set theme, settings?
        if (themeVariant == null) {
            throw new RuntimeException("No theme selected for "+renderedLeaf.toString());
        }
        RenderingContext renderingContext = new RenderingContext(themeVariant, leaf);
        for (String contentArea : leaf.getContentAreas()) {
            for (UIElement uiElement : leaf.getUIElements(contentArea)) {
                decorate(uiElement, renderingContext);
            }
        }
        return renderedLeaf;
    }

    public static String decorate(UIElement uiElement, RenderingContext renderingContext) {
        Map<UIElementType, CachedDecorator> decorators = Themes.getDecoratorsForTheme(renderingContext.getThemeVariant().themeId);
        renderingContext.nestle(uiElement);
        String decoratedOutput = null;
        if (decorators.containsKey(uiElement.getType())) {
            CachedDecorator decorator = decorators.get(uiElement.getType());
            decoratedOutput = ReflectionHelper.invokeDecorator(decorator.method, null);
        }
        if (decoratedOutput == null) {
            decoratedOutput = DefaultDecorator.decorate(uiElement, renderingContext);
        }
        renderingContext.unNestle();
        return decoratedOutput;
    }
    
    public static String decorate(List<UIElement> uiElements, RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder();
        for (UIElement childElement : uiElements) {
            sb.append(decorate(childElement, renderingContext));
        }
        return sb.toString();
    }
}

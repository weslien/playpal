package helpers;

import models.cmscore.Settings;
import models.cmscore.SettingsKeys;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import play.modules.cmscore.CachedThemeVariant;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Themes;
import play.modules.cmscore.annotations.CachedDecorator;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.RenderedLeaf;
import play.modules.cmscore.ui.RenderingContext;
import play.modules.cmscore.ui.UIElement;

import java.util.Map;

public class ThemeHelper {
    
    private static final Logger LOG = Logger.getLogger(ThemeHelper.class);

    public static RenderedLeaf decorate(Leaf leaf) {
        RenderedLeaf renderedLeaf = new RenderedLeaf();
        CachedThemeVariant themeVariant = loadTheme(leaf, renderedLeaf);
        renderedLeaf.setTitle(leaf.getTitle());
        renderedLeaf.setTemplate(ReflectionHelper.getTemplate(themeVariant));
        RenderingContext renderingContext = new RenderingContext(themeVariant, leaf);
        for (String contentArea : leaf.getContentAreas()) {
            for (UIElement uiElement : leaf.getUIElements(contentArea)) {
                decorate(uiElement, renderingContext);
            }
        }
        return renderedLeaf;
    }

    public static String decorate(UIElement uiElement, play.modules.cmscore.ui.RenderingContext renderingContext) {
        Map<UIElementType, CachedDecorator> decorators = Themes.getDecoratorsForTheme(renderingContext.getThemeVariant().themeId);
        renderingContext.nestle(uiElement);
        String decoratedOutput = null;
        if (decorators.containsKey(uiElement.getType())) {
            CachedDecorator decorator = decorators.get(uiElement.getType());
            decoratedOutput = ReflectionHelper.invokeDecorator(decorator, null);
        }
        if (decoratedOutput == null) {
            decoratedOutput = DefaultDecorator.decorate(uiElement, renderingContext);
        }
        renderingContext.unNestle();
        return decoratedOutput;
    }

    public static String decorateChildren(UIElement parent, play.modules.cmscore.ui.RenderingContext renderingContext) {
        StringBuilder sb = new StringBuilder();
        renderingContext.nestle(parent);
        for (UIElement childElement : parent.getChildren()) {
            sb.append(decorate(childElement, renderingContext));
        }
        renderingContext.unNestle();
        return sb.toString();
    }

    private static CachedThemeVariant loadTheme(Leaf leaf, play.modules.cmscore.ui.RenderedLeaf renderedLeaf) {
        CachedThemeVariant themeVariant = Themes.getThemeVariant(leaf.getThemeVariant());
        if (themeVariant == null) {
            Settings settings = Settings.load();
            String themeVariantId = settings.getValue(SettingsKeys.Core.THEME_VARIANT);
            if (StringUtils.isEmpty(themeVariantId)) {
                throw new RuntimeException("No theme set for leaf and no default theme variant set");
            }
            LOG.debug("Using default theme variant ["+themeVariantId+"]");
            themeVariant = Themes.getThemeVariant(themeVariantId);
        }
        if (themeVariant == null) {
            // TODO: Add some sort of fallback for when a theme is removed
            throw new RuntimeException("No theme selected for "+renderedLeaf.toString());
        }
        return themeVariant;
    }

}

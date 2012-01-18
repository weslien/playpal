package helpers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import play.modules.cmscore.CachedThemeVariant;
import play.modules.cmscore.Node;
import play.modules.cmscore.Themes;
import play.modules.cmscore.annotations.CachedDecorator;
import play.modules.cmscore.annotations.UIElementType;
import play.modules.cmscore.ui.RenderedNode;
import play.modules.cmscore.ui.RenderingContext;
import play.modules.cmscore.ui.UIElement;

import java.util.HashMap;
import java.util.Map;

public class ThemeHelper {

    private static final Logger LOG = Logger.getLogger(ThemeHelper.class);

    public static RenderedNode decorate(Node node) {
        RenderedNode renderedNode = new RenderedNode(node.getNodeId());
        CachedThemeVariant themeVariant = loadTheme(node);
        setupRegions(themeVariant, renderedNode);
        renderedNode.setTitle(node.getTitle());
        renderedNode.setTemplate(ReflectionHelper.getTemplate(themeVariant));
        RenderingContext renderingContext = new RenderingContext(themeVariant, node);
        for (String region : node.getRegions()) {
            for (UIElement uiElement : node.getUIElements(region)) {
                String decoratedContent = decorate(uiElement, renderingContext);
                switch (uiElement.getType()) {
                    case META:
                        if (Node.HEAD.equalsIgnoreCase(region)) {
                            renderedNode.addMeta(decoratedContent);
                            break;
                        } else {
                            throw new RuntimeException("META is not allowed outside of head");
                        }
                    case LINK:
                        if (Node.HEAD.equalsIgnoreCase(region)) {
                            renderedNode.addLink(decoratedContent);
                            break;
                        } else {
                            throw new RuntimeException("LINK is not allowed outside of head");
                        }
                    case SCRIPT:
                        if (Node.HEAD.equalsIgnoreCase(region)) {
                            renderedNode.addScript(decoratedContent);
                            break;
                        }
                    case STYLE:
                        if (Node.HEAD.equalsIgnoreCase(region)) {
                            renderedNode.addStyle(decoratedContent);
                            break;
                        }
                    default:
                        renderedNode.add(region, decoratedContent);
                }
            }
        }
        return renderedNode;
    }

    /**
     * Sets all the regions in the rendered node so the template can access them without
     * nullpointer even if the page has no ui elements.
     *
     * @param themeVariant the theme variant that holds the regions available
     * @param renderedNode the node about to rendered
     */
    private static void setupRegions(CachedThemeVariant themeVariant, RenderedNode renderedNode) {
        Map<String, String> regions = new HashMap<String, String>();
        for (String region : themeVariant.regions) {
            regions.put(region, "");
        }
        renderedNode.setRegions(regions);
    }

    public static String decorate(UIElement uiElement, RenderingContext renderingContext) {
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

    public static String decorateChildren(UIElement parent, RenderingContext renderingContext) {
        if (!parent.hasChildren()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        renderingContext.nestle(parent);
        for (UIElement childElement : parent.getChildren()) {
            sb.append(decorate(childElement, renderingContext));
        }
        renderingContext.unNestle();
        return sb.toString();
    }

    private static CachedThemeVariant loadTheme(Node node) {
        CachedThemeVariant themeVariant = Themes.getThemeVariant(node.getThemeVariant());
        if (themeVariant == null) {
            String themeVariantId = SettingsHelper.getThemeVariant();
            if (StringUtils.isEmpty(themeVariantId)) {
                throw new RuntimeException("No theme set for node and no default theme variant set");
            }
            LOG.debug("Using default theme variant [" + themeVariantId + "]");
            themeVariant = Themes.getThemeVariant(themeVariantId);
        }
        if (themeVariant == null) {
            // TODO: Add some sort of fallback for when a theme is removed
            throw new RuntimeException("No theme selected for " + node.toString());
        }
        return themeVariant;
    }

}
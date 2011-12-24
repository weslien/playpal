package play.modules.cmscore;

import play.modules.cmscore.annotations.CachedDecorator;
import play.modules.cmscore.annotations.UIElementType;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Themes are used to render Leafs. A theme has multiple theme variants, for example 2-columns, 3-columns, top-middle-bottom, etc.
 * Each leaf has a chosen theme variant to use for rendering.
 * Decorators are applied to each UIElement a Leaf has before rendering.
 */
public class Themes {

    /**
     * Collection of theme variants id's that a theme offers.
     */
    public static Map<String, CachedTheme> themes = new HashMap<String, CachedTheme>();

    /**
     * Maps variant id's to it's parent theme id.
     */
    private static Map<String, String> themeVariantsToThemeMapping = new HashMap<String, String>();
    
    public static void addThemeVariant(String themeId, String variantId, Method templateMethod, String[] contentAreas) {
        // Themes are declared on the class level and should be parsed first so we don't need to check if the themeId exists before accessing
        Map<String, CachedThemeVariant> themeVariants = themes.get(themeId).getThemeVariants();
        
        if (themeVariants.containsKey(variantId)) {
            throw new RuntimeException("Duplicate theme variant id [" + variantId + "]");
        }
        themeVariantsToThemeMapping.put(variantId, themeId);
        themeVariants.put(variantId, new CachedThemeVariant(themeId, variantId, templateMethod, new HashSet<String>(Arrays.asList(contentAreas))));
    }
    
    public static void addDecorator(String themeId, UIElementType UIElementType, Method method) {
        // Themes are declared on the class level and should be parsed first so we don't need to check if the themeId exists before accessing
        Map<UIElementType, CachedDecorator> themeDecorators = themes.get(themeId).getDecorators();

        themeDecorators.put(UIElementType, new CachedDecorator(method));
    }

    public static CachedTheme getTheme(String themeId) {
        return themes.get(themeId);
    }

    public static CachedThemeVariant getThemeVariant(String themeId, String variantId) {
        return themes.get(themeId).getThemeVariants().get(variantId);
    }

    public static CachedThemeVariant getThemeVariant(String variantId) {
        if (themeVariantsToThemeMapping.containsKey(variantId)) {
            return getThemeVariant(themeVariantsToThemeMapping.get(variantId), variantId);
        } else {
            return null;
        }
    }
    
    public static Collection<CachedThemeVariant> getThemeVariants(String themeId) {
        if (themes.containsKey(themeId)) {
            return themes.get(themeId).getThemeVariants().values();
        }
        return Collections.emptyList();
    }

    public static Map<UIElementType, CachedDecorator> getDecoratorsForTheme(String themeId) {
        if (themes.containsKey(themeId)) {
            Map<UIElementType, CachedDecorator> decorators = themes.get(themeId).getDecorators();
            if (decorators != null) {
                return decorators;
            }
        }
        return Collections.emptyMap();
    }
    
    public static CachedDecorator getDecoratorForTheme(String themeId, UIElementType UIElementType) {
        return getDecoratorsForTheme(themeId).get(UIElementType);
    }

    /**
     * Invalidates all themes and all theme variants that have been created with annotations in the class
     * specified so that we can reload the themes and theme variants from the new class.
     * @param cls the class that has been re-compiled
     */
    public static void invalidate(Class cls) {
        for (Iterator<CachedTheme> themeIterator = themes.values().iterator(); themeIterator.hasNext(); ) {
            CachedTheme theme = themeIterator.next();
            if (theme.getDeclaringClass().equals(cls)) {
                themeIterator.remove();
            }
            for (Iterator<CachedThemeVariant> themeVariantIterator = theme.getThemeVariants().values().iterator(); themeVariantIterator.hasNext(); ) {
                CachedThemeVariant themeVariant = themeVariantIterator.next();
                if (themeVariant.templateMethod.getDeclaringClass().equals(cls)) {
                    themeVariantsToThemeMapping.remove(themeVariant.variantId);
                    themeVariantIterator.remove();
                }
            }
        }
    }
    
}

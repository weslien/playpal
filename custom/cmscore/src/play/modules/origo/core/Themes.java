package play.modules.origo.core;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Themes are used to render Nodes. A theme has multiple theme variants, for example 2-columns, 3-columns, top-middle-bottom, etc.
 * Each rootNode has a chosen theme variant to use for rendering.
 * Decorators are applied to each UIElement a RootNode has before rendering.
 */
public class Themes {

    /**
     * Collection of theme variants id's that a theme offers.
     */
    public static Map<String, CachedTheme> themes = new ConcurrentHashMap<String, CachedTheme>();

    /**
     * Maps variant id's to it's parent theme id.
     */
    private static Map<String, String> themeVariantsToThemeMapping = new HashMap<String, String>();

    public static void addTheme(String themeId, Class declaringClass) {
        if (themes.containsKey(themeId)) {
            throw new RuntimeException("Theme [" + themeId + "] declared in both " + declaringClass.getName() + " and " + themes.get(themeId).getDeclaringClass().getName());
        }
        themes.put(themeId, new CachedTheme(themeId, declaringClass));

    }

    public static void addThemeVariant(String themeId, String variantId, Method templateMethod, String[] regions) {
        // Themes are declared on the class level and should be parsed first so we don't need to check if the themeId exists before accessing
        Map<String, play.modules.origo.core.CachedThemeVariant> themeVariants = themes.get(themeId).getThemeVariants();

        if (themeVariants.containsKey(variantId)) {
            throw new RuntimeException("Duplicate theme variant id [" + variantId + "]");
        }
        themeVariantsToThemeMapping.put(variantId, themeId);
        themeVariants.put(variantId, new play.modules.origo.core.CachedThemeVariant(themeId, variantId, templateMethod, new HashSet<String>(Arrays.asList(regions))));
    }

    public static void addDecorator(String themeId, play.modules.origo.core.annotations.UIElementType UIElementType, Method method) {
        // Themes are declared on the class level and should be parsed first so we don't need to check if the themeId exists before accessing
        Map<play.modules.origo.core.annotations.UIElementType, play.modules.origo.core.annotations.CachedDecorator> themeDecorators = themes.get(themeId).getDecorators();

        themeDecorators.put(UIElementType, new play.modules.origo.core.annotations.CachedDecorator(method));
    }

    public static CachedTheme getTheme(String themeId) {
        return themes.get(themeId);
    }

    public static play.modules.origo.core.CachedThemeVariant getThemeVariant(String themeId, String variantId) {
        return themes.get(themeId).getThemeVariants().get(variantId);
    }

    public static play.modules.origo.core.CachedThemeVariant getThemeVariant(String variantId) {
        if (themeVariantsToThemeMapping.containsKey(variantId)) {
            return getThemeVariant(themeVariantsToThemeMapping.get(variantId), variantId);
        } else {
            return null;
        }
    }

    public static Collection<play.modules.origo.core.CachedThemeVariant> getThemeVariants(String themeId) {
        if (themes.containsKey(themeId)) {
            return themes.get(themeId).getThemeVariants().values();
        }
        return Collections.emptyList();
    }

    public static Map<play.modules.origo.core.annotations.UIElementType, play.modules.origo.core.annotations.CachedDecorator> getDecoratorsForTheme(String themeId) {
        if (themes.containsKey(themeId)) {
            Map<play.modules.origo.core.annotations.UIElementType, play.modules.origo.core.annotations.CachedDecorator> decorators = themes.get(themeId).getDecorators();
            if (decorators != null) {
                return decorators;
            }
        }
        return Collections.emptyMap();
    }

    public static play.modules.origo.core.annotations.CachedDecorator getDecoratorForTheme(String themeId, play.modules.origo.core.annotations.UIElementType UIElementType) {
        return getDecoratorsForTheme(themeId).get(UIElementType);
    }

    /**
     * Invalidates all themes and all theme variants that have been created with annotations in the class
     * specified so that we can reload the themes and theme variants from the new class.
     *
     * @param cls the class that has been re-compiled
     */
    public static void invalidate(Class cls) {
        for (Iterator<CachedTheme> themeIterator = themes.values().iterator(); themeIterator.hasNext(); ) {
            CachedTheme theme = themeIterator.next();
            if (theme.getDeclaringClass().getCanonicalName().equals(cls.getCanonicalName())) {
                themeIterator.remove();
                for (Iterator<play.modules.origo.core.CachedThemeVariant> themeVariantIterator = theme.getThemeVariants().values().iterator(); themeVariantIterator.hasNext(); ) {
                    play.modules.origo.core.CachedThemeVariant themeVariant = themeVariantIterator.next();
                    if (themeVariant.templateMethod.getDeclaringClass().getCanonicalName().equals(cls.getCanonicalName())) {
                        themeVariantsToThemeMapping.remove(themeVariant.variantId);
                        themeVariantIterator.remove();
                    }
                }
            }
        }
    }

}

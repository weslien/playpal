package play.modules.cmscore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds a variant for a theme, the method it annotates returns a String path to a template.
 * Only valid in a class annotated by \@Theme.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ThemeVariant {

    /**
     * A unique id for referencing this variant. Used as the key when caching this theme
     * variant.
     * Best practice is to include the theme's id in the id, for example:
     * themeId=Basic would give variantId=Basic-Green and variantId=Basic-Red
     * @return A unique id for this variant.
     */
    String id();

    /**
     * The content areas this theme variant offers.
     * @return an array of content area names
     */
    String[] contentAreas();

    /**
     * XML, HTML, TEXT, PDF, etc.
     */
    String output() default "HTML";
}

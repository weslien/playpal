package play.modules.cmscore;

import play.modules.cmscore.annotations.CachedDecorator;
import play.modules.cmscore.annotations.UIElementType;

import java.util.HashMap;
import java.util.Map;

public class CachedTheme {

    /**
     * Commomn identifier for this theme.
     */
    private final String themeId;

    /**
     * The class where the theme is declared.
     */
    private final Class declaringClass;

    /**
     * Collection of cached theme variants, with their template method and the content areas they contain.
     * @see CachedThemeVariant
     */
    public final Map<String, CachedThemeVariant> themeVariants = new HashMap<String, CachedThemeVariant>();

    /**
     * Collection of decorators for each theme that can transform UIElements into elements in a RenderedLeaf.
     * @see play.modules.cmscore.ui.UIElement
     * @see RenderedLeaf
     */
    public final Map<UIElementType, CachedDecorator> decorators = new HashMap<UIElementType, CachedDecorator>();

    public CachedTheme(String themeId, Class declaringClass) {
        this.themeId = themeId;
        this.declaringClass = declaringClass;
    }

    public String getThemeId() {
        return themeId;
    }

    public Class getDeclaringClass() {
        return declaringClass;
    }

    public Map<String, CachedThemeVariant> getThemeVariants() {
        return themeVariants;
    }

    public Map<UIElementType, CachedDecorator> getDecorators() {
        return decorators;
    }

}

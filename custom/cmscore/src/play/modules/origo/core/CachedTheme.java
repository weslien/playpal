package play.modules.origo.core;

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
     *
     * @see CachedThemeVariant
     */
    public final Map<String, CachedThemeVariant> themeVariants = new HashMap<String, CachedThemeVariant>();

    /**
     * Collection of decorators for each theme that can transform UIElements into elements in a RenderedNode.
     *
     * @see play.modules.origo.core.ui.UIElement
     * @see play.modules.origo.core.ui.RenderedNode
     */
    public final Map<play.modules.origo.core.annotations.UIElementType, play.modules.origo.core.annotations.CachedDecorator> decorators = new HashMap<play.modules.origo.core.annotations.UIElementType, play.modules.origo.core.annotations.CachedDecorator>();

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

    public Map<play.modules.origo.core.annotations.UIElementType, play.modules.origo.core.annotations.CachedDecorator> getDecorators() {
        return decorators;
    }

}

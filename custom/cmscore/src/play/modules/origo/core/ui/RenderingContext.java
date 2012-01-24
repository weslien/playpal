package play.modules.origo.core.ui;

import java.util.Stack;

public class RenderingContext {

    private final play.modules.origo.core.CachedThemeVariant themeVariant;
    private final play.modules.origo.core.Node rootNode;
    private final Stack<UIElement> parents;

    public RenderingContext(play.modules.origo.core.CachedThemeVariant themeVariant, play.modules.origo.core.Node rootNode) {
        this.themeVariant = themeVariant;
        this.rootNode = rootNode;
        this.parents = new Stack<UIElement>();
    }

    public play.modules.origo.core.CachedThemeVariant getThemeVariant() {
        return themeVariant;
    }

    public play.modules.origo.core.Node getRootNode() {
        return rootNode;
    }

    public UIElement getParent() {
        return parents.peek();
    }

    public void nestle(UIElement parent) {
        parents.push(parent);
    }

    public void unNestle() {
        parents.pop();
    }
}

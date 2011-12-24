package play.modules.cmscore.ui;

import play.modules.cmscore.CachedThemeVariant;
import play.modules.cmscore.Leaf;

import java.util.Stack;

public class RenderingContext {

    private final CachedThemeVariant themeVariant;
    private final Leaf rootLeaf;
    private final Stack<UIElement> parents;

    public RenderingContext(CachedThemeVariant themeVariant, Leaf rootLeaf) {
        this.themeVariant = themeVariant;
        this.rootLeaf = rootLeaf;
        this.parents = new Stack<UIElement>();
    }

    public CachedThemeVariant getThemeVariant() {
        return themeVariant;
    }

    public Leaf getRootLeaf() {
        return rootLeaf;
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

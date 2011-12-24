package play.modules.cmscore;

import play.modules.cmscore.ui.UIElement;

import java.util.Stack;

public class RenderingContext {

    private final CachedThemeVariant themeVariant;
    private final LeafType rootLeaf;
    private final Stack<UIElement> parents;

    public RenderingContext(CachedThemeVariant themeVariant, LeafType rootLeaf) {
        this.themeVariant = themeVariant;
        this.rootLeaf = rootLeaf;
        this.parents = new Stack<UIElement>();
    }

    public CachedThemeVariant getThemeVariant() {
        return themeVariant;
    }

    public LeafType getRootLeaf() {
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

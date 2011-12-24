package play.modules.cmscore.ui;

import play.modules.cmscore.annotations.UIElementType;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

// TODO: Should UIElement's be separated into two types, one with body and one without?
public class UIElement {

    public String id;

    public UIElementType type;

    public Map<String, String> attributes;

    private int weight;

    private List<UIElement> children;

    private String body;

    public UIElement(String id, UIElementType type, int weight) {
        this(id, type, new WeakHashMap<String, String>(), weight);
    }
    
    public UIElement(String id, UIElementType type, Map<String, String> attributes, int weight) {
        this.id = id;
        this.type = type;
        this.attributes = attributes;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UIElementType getType() {
        return type;
    }

    public void setType(UIElementType type) {
        this.type = type;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<UIElement> getChildren() {
        return children;
    }

    public void setChildren(List<UIElement> children) {
        this.children = children;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

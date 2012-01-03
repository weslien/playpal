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

    public UIElement(UIElementType type, int weight) {
        this("", type, new WeakHashMap<String, String>(), weight, null);
    }
    
    public UIElement(UIElementType type, int weight, String body) {
        this("", type, new WeakHashMap<String, String>(), weight, body);
    }
    
    public UIElement(String id, UIElementType type, int weight) {
        this(id, type, new WeakHashMap<String, String>(), weight, null);
    }

    public UIElement(String id, UIElementType type, int weight, String body) {
        this(id, type, new WeakHashMap<String, String>(), weight, body);
    }

    public UIElement(UIElementType type, Map<String, String> attributes, int weight) {
        this("", type, attributes, weight, null);
    }

    public UIElement(UIElementType type, Map<String, String> attributes, int weight, String body) {
        this("", type, attributes, weight, body);
    }

    public UIElement(String id, UIElementType type, Map<String, String> attributes, int weight, String body) {
        this.id = id;
        this.type = type;
        this.attributes = attributes;
        this.weight = weight;
        this.body = body;
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

    public UIElement addChild(UIElement uiElement) {
        this.children.add(uiElement);
        return uiElement;
    }

    public boolean removeChild(UIElement uiElement) {
        return this.children.remove(uiElement);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

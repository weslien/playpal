package play.modules.origo.core.ui;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

// TODO: Should UIElement's be separated into two types, one with body and one without?
public class UIElement {

    public String id;

    public play.modules.origo.core.annotations.UIElementType type;

    public Map<String, String> attributes;

    private int weight;

    private List<UIElement> children = new ArrayList<UIElement>();

    private String body;

    public UIElement(play.modules.origo.core.annotations.UIElementType type) {
        this("", type, new WeakHashMap<String, String>(), 0, null);
    }

    public UIElement(play.modules.origo.core.annotations.UIElementType type, int weight) {
        this("", type, new WeakHashMap<String, String>(), weight, null);
    }

    public UIElement(play.modules.origo.core.annotations.UIElementType type, String body) {
        this("", type, new WeakHashMap<String, String>(), 0, body);
    }

    public UIElement(play.modules.origo.core.annotations.UIElementType type, int weight, String body) {
        this("", type, new WeakHashMap<String, String>(), weight, body);
    }

    public UIElement(String id, play.modules.origo.core.annotations.UIElementType type) {
        this(id, type, new WeakHashMap<String, String>(), 0, null);
    }

    public UIElement(String id, play.modules.origo.core.annotations.UIElementType type, int weight) {
        this(id, type, new WeakHashMap<String, String>(), weight, null);
    }

    public UIElement(String id, play.modules.origo.core.annotations.UIElementType type, String body) {
        this(id, type, new WeakHashMap<String, String>(), 0, body);
    }

    public UIElement(String id, play.modules.origo.core.annotations.UIElementType type, int weight, String body) {
        this(id, type, new WeakHashMap<String, String>(), weight, body);
    }

    public UIElement(play.modules.origo.core.annotations.UIElementType type, Map<String, String> attributes) {
        this("", type, attributes, 0, null);
    }

    public UIElement(play.modules.origo.core.annotations.UIElementType type, Map<String, String> attributes, int weight) {
        this("", type, attributes, weight, null);
    }

    public UIElement(play.modules.origo.core.annotations.UIElementType type, Map<String, String> attributes, String body) {
        this("", type, attributes, 0, body);
    }

    public UIElement(play.modules.origo.core.annotations.UIElementType type, Map<String, String> attributes, int weight, String body) {
        this("", type, attributes, weight, body);
    }

    public UIElement(String id, play.modules.origo.core.annotations.UIElementType type, Map<String, String> attributes, int weight, String body) {
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

    public play.modules.origo.core.annotations.UIElementType getType() {
        return type;
    }

    public void setType(play.modules.origo.core.annotations.UIElementType type) {
        this.type = type;
    }

    public boolean hasAttributes() {
        return getAttributes() != null && !getAttributes().isEmpty();
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void put(String name, String value) {
        this.attributes.put(name, value);
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean hasChildren() {
        return getChildren() != null && !getChildren().isEmpty();
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

    public boolean hasBody() {
        return !StringUtils.isBlank(getBody());
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

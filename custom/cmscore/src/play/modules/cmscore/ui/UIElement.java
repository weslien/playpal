package play.modules.cmscore.ui;

import java.util.Map;
import java.util.WeakHashMap;

public class UIElement {

    private String id;

    private String name;

    private Map<String, String> attributes;

    private int weight;

    public UIElement(String id, String name, int weight) {
        this(id, name, new WeakHashMap<String, String>(), weight);
    }
    
    public UIElement(String id, String name, Map<String, String> attributes, int weight) {
        this.id = id;
        this.name = name;
        this.attributes = attributes;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}

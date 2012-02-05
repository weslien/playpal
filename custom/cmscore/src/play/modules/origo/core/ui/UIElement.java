package play.modules.origo.core.ui;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

// TODO: Should UIElement's be separated into two types, one with body and one without?
public class UIElement {

    public static final String META = "meta";
    public static final String SCRIPT = "script";
    public static final String STYLE = "style";
    public static final String LINK = "link";

    public static final String LIST_BULLET = "list_bullet";
    public static final String LIST_ORDERED = "list_ordered";
    public static final String LIST_ITEM = "list_item";

    public static final String FORM = "form";
    public static final String LABEL = "label";
    public static final String INPUT_HIDDEN = "input_hidden";
    public static final String INPUT_TEXT = "input_text";
    public static final String INPUT_TEXTAREA = "input_textarea";
    public static final String INPUT_RADIO_BUTTON = "input_radio_button";
    public static final String INPUT_SELECT = "input_select";
    public static final String INPUT_SELECT_OPTION = "input_select_option";
    public static final String INPUT_BUTTON = "input_button";
    public static final String INPUT_SUBMIT = "input_submit";
    public static final String INPUT_RESET = "input_reset";
    public static final String INPUT_IMAGE = "input_image";
    public static final String INPUT_FILE = "input_file";
    public static final String INPUT_PASSWORD = "input_password";

    public static final String PANEL = "panel";
    public static final String TEXT = "text";
    public static final String ANCHOR = "anchor";


    public String id;

    public String type;

    public Map<String, String> attributes;

    private int weight;

    private List<UIElement> children = new ArrayList<UIElement>();

    private String body;

    public UIElement(String type) {
        this("", type, 0, null);
    }

    public UIElement(String type, int weight) {
        this("", type, weight, null);
    }

    public UIElement(String type, int weight, String body) {
        this("", type, weight, body);
    }

    public UIElement(String id, String type) {
        this(id, type, 0, null);
    }

    public UIElement(String id, String type, int weight) {
        this(id, type, weight, null);
    }

    public UIElement(String id, String type, String body) {
        this(id, type, 0, body);
    }

    public UIElement(String id, String type, int weight, String body) {
        this.id = id;
        this.type = type;
        this.attributes = new WeakHashMap<String, String>();
        this.weight = weight;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public UIElement setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public UIElement setType(String type) {
        this.type = type;
        return this;
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

    public UIElement addAttribute(String name, String value) {
        this.attributes.put(name, value);
        return this;
    }

    public int getWeight() {
        return this.weight;
    }

    public UIElement setWeight(int weight) {
        this.weight = weight;
        return this;
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
        return this;
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

    public UIElement setBody(String body) {
        this.body = body;
        return this;
    }
}

package models.origo.admin;

import origo.helpers.UIElementHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;

import java.util.*;

public class AdminPage implements Node {

    private static final String CONTENT = "content";

    public String nodeId;

    public String title;

    public String themeVariant;

    private Map<String, List<UIElement>> uiElements = new HashMap<String, List<UIElement>>();

    public AdminPage(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String getNodeId() {
        return this.nodeId;
    }

    @Override
    public Long getVersion() {
        return 1L;
    }

    @Override
    public Date getDatePublished() {
        return new Date();
    }

    @Override
    public Date getDateUnpublished() {
        return new Date();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getThemeVariant() {
        return themeVariant;
    }

    public void setThemeVariant(String themeVariant) {
        this.themeVariant = themeVariant;
    }

    @Override
    public Set<String> getRegions() {
        return this.uiElements.keySet();
    }

    /* Interface methods */
    @Override
    public List<UIElement> getUIElements(String region) {
        return this.uiElements.get(region.toLowerCase());
    }

    @Override
    public UIElement addHeadUIElement(UIElement uiElement) {
        return addHeadUIElement(uiElement, false);
    }

    @Override
    public UIElement addUIElement(UIElement uiElement) {
        return addUIElement(uiElement, false);
    }

    @Override
    public UIElement addHeadUIElement(UIElement uiElement, boolean reorderElementsBelow) {
        return addUIElement(uiElement, reorderElementsBelow, HEAD, 0);
    }

    @Override
    public UIElement addUIElement(UIElement uiElement, boolean reorderElementsBelow) {
        return addUIElement(uiElement, reorderElementsBelow, CONTENT, uiElement.getWeight());
    }

    private UIElement addUIElement(UIElement uiElement, boolean reorderElementsBelow, String regionKey, int weight) {
        if (!uiElements.containsKey(regionKey)) {
            uiElements.put(regionKey, new ArrayList<UIElement>());
        }
        uiElement.setWeight(weight);
        uiElements.get(regionKey).add(uiElement);
        if (reorderElementsBelow) {
            UIElementHelper.repositionUIElements(uiElements.get(regionKey), uiElement);
        }
        UIElementHelper.reorderUIElements(uiElements.get(regionKey));
        return uiElement;
    }

    @Override
    public boolean removeHeadUIElement(UIElement uiElement) {
        return removeUIElement(uiElement, HEAD);
    }

    @Override
    public boolean removeUIElement(UIElement uiElement) {
        return removeUIElement(uiElement, CONTENT);
    }

    private boolean removeUIElement(UIElement uiElement, String regionKey) {
        if (uiElements.get(regionKey).remove(uiElement)) {
            UIElementHelper.reorderUIElements(uiElements.get(regionKey));
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("AdminPage {").
                append("nodeId='").append(nodeId).append("\', ").
                append("title='").append(title).append("\', ").
                append("themeVariant='").append(themeVariant).append("\', ").
                append("uiElements=").append(uiElements).append('}').
                toString();
    }
}

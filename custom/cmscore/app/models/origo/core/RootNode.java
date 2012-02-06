package models.origo.core;

import origo.helpers.UIElementHelper;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "nodeVersion", columnNames = {"nodeId", "version"}))
public final class RootNode extends Model implements Node {

    @Required
    public String nodeId;

    // TODO: Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3 (2.0?)
    @Required
    public Long version;

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date publish;

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date unPublish;

    public String type;

    public String themeVariant;

    @Transient
    private Map<String, List<UIElement>> uiElements = new HashMap<String, List<UIElement>>();

    public RootNode(Long version) {
        this(UUID.randomUUID().toString(), version);
    }

    public RootNode(String nodeId, Long version) {
        this.nodeId = nodeId;
        this.version = version;
    }

    @Override
    public String getTitle() {
        return toString();
    }

    @Override
    public String getNodeId() {
        return nodeId;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public Date getDatePublished() {
        return publish;
    }

    @Override
    public Date getDateUnpublished() {
        return unPublish;
    }

    @Override
    public String getThemeVariant() {
        return themeVariant;
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
        return addUIElement(uiElement, reorderElementsBelow, HEAD, uiElement.getWeight());
    }

    @Override
    public UIElement addUIElement(UIElement uiElement, boolean reorderElementsBelow) {
        Meta meta = Meta.findWithNodeIdAndSpecificVersion(nodeId, version, uiElement.id);
        if (meta == null) {
            meta = Meta.defaultMeta();
        }

        String regionKey = meta.region.toLowerCase();
        return addUIElement(uiElement, reorderElementsBelow, regionKey, meta.weight.intValue());
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
        Meta meta = Meta.findWithNodeIdAndSpecificVersion(nodeId, version, uiElement.id);
        if (meta == null) {
            meta = Meta.defaultMeta();
        }
        String regionKey = meta.region.toLowerCase();
        return removeUIElement(uiElement, regionKey);
    }

    private boolean removeUIElement(UIElement uiElement, String regionKey) {
        if (uiElements.get(regionKey).remove(uiElement)) {
            UIElementHelper.reorderUIElements(uiElements.get(regionKey));
            return true;
        }
        return false;
    }

    public static List<RootNode> findAllCurrentVersions(Date today) {
        List<RootNode> leaves = RootNode.find(
                "select l from RootNode l " +
                        "where l.version = (" +
                        "select max(l2.version) from RootNode l2 " +
                        "where l2.nodeId = l.nodeId and " +
                        "(l2.publish = null or l2.publish < :today) and " +
                        "(l2.unPublish = null or l2.unPublish >= :today)" +
                        ")"
        ).bind("today", today).fetch();
        for (RootNode node : leaves) {
            initializeNode(node);
        }
        return leaves;
    }

    public static RootNode findWithNodeIdAndSpecificVersion(String nodeId, Long version) {
        RootNode node = RootNode.find(
                "select distinct l from RootNode l " +
                        "where l.nodeId = :nodeId and " +
                        "l.version = :version"
        ).bind("nodeId", nodeId).bind("version", version).first();
        if (node != null) {
            initializeNode(node);
        }
        return node;
    }

    public static RootNode findLatestPublishedVersionWithNodeId(String nodeId, Date today) {
        RootNode node = RootNode.find(
                "select distinct l from RootNode l " +
                        "where l.nodeId = :nodeId and " +
                        "(l.publish = null or l.publish < :today) and " +
                        "(l.unPublish = null OR l.unPublish >= :today)" +
                        "order by version desc"
        ).bind("nodeId", nodeId).bind("today", today).first();
        if (node != null) {
            initializeNode(node);
        }
        return node;
    }

    public static RootNode findLatestVersionWithNodeId(String nodeId) {
        RootNode node = RootNode.find(
                "select distinct r from RootNode r " +
                        "where r.nodeId = :nodeId " +
                        "order by version desc"
        ).bind("nodeId", nodeId).first();
        if (node != null) {
            initializeNode(node);
        }
        return node;
    }

    public static List<RootNode> findAllVersionsWithNodeId(String nodeId) {
        List<RootNode> leaves = RootNode.find(
                "select distinct l from RootNode l where l.nodeId = :nodeId"
        ).bind("nodeId", nodeId).fetch();
        for (RootNode node : leaves) {
            initializeNode(node);
        }
        return leaves;
    }

    private static void initializeNode(RootNode node) {
        node.uiElements = new HashMap<String, List<UIElement>>();
        node.uiElements.put(HEAD, new ArrayList<UIElement>());
    }

    @Override
    public String toString() {
        return "Node (" + nodeId + "," + version + ")";
    }
}

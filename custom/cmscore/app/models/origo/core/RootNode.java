package models.origo.core;

import origo.helpers.UIElementHelper;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.origo.core.Node;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "nodeVersion", columnNames = {"uuid", "version"}))
public final class RootNode extends Model implements Node {

    @Required
    public String uuid;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    public Long version;

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date publish;

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date unPublish;

    public String type;

    public String themeVariant;

    @Transient
    private Map<String, List<play.modules.origo.core.ui.UIElement>> uiElements = new HashMap<String, List<play.modules.origo.core.ui.UIElement>>();

    public RootNode(Long version) {
        this.uuid = UUID.randomUUID().toString();
        this.version = version;
    }

    public RootNode(String uuid, Long version) {
        this.uuid = uuid;
        this.version = version;
    }

    @Override
    public String getTitle() {
        return toString();
    }

    @Override
    public String getNodeId() {
        return uuid;
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
    public List<play.modules.origo.core.ui.UIElement> getUIElements(String region) {
        return this.uiElements.get(region.toLowerCase());
    }

    @Override
    public play.modules.origo.core.ui.UIElement addUIElement(play.modules.origo.core.ui.UIElement uiElement) {
        return addUIElement(HEAD, uiElement, false);
    }

    @Override
    public play.modules.origo.core.ui.UIElement addUIElement(String region, play.modules.origo.core.ui.UIElement uiElement) {
        return addUIElement(region, uiElement, false);
    }

    @Override
    public play.modules.origo.core.ui.UIElement addUIElement(String region, play.modules.origo.core.ui.UIElement uiElement, boolean reorderElementsBelow) {
        String regionKey = region.toLowerCase();
        if (!uiElements.containsKey(regionKey)) {
            uiElements.put(regionKey, new ArrayList<play.modules.origo.core.ui.UIElement>());
        }
        uiElements.get(regionKey).add(uiElement);
        if (reorderElementsBelow) {
            UIElementHelper.repositionUIElements(uiElements.get(regionKey), uiElement);
        }
        UIElementHelper.reorderUIElements(uiElements.get(regionKey));
        return uiElement;
    }

    @Override
    public boolean removeUIElement(String region, play.modules.origo.core.ui.UIElement uiElement) {
        String regionKey = region.toLowerCase();
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
                        "where l2.uuid = l.uuid and " +
                        "(l2.publish = null or l2.publish < :today) and " +
                        "(l2.unPublish = null or l2.unPublish >= :today)" +
                        ")"
        ).bind("today", today).fetch();
        for (RootNode node : leaves) {
            initializeNode(node);
        }
        return leaves;
    }

    public static RootNode findWithUuidSpecificVersion(String uuid, Long version) {
        RootNode node = RootNode.find(
                "select distinct l from RootNode l " +
                        "where l.uuid = :uuid and " +
                        "l.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
        if (node != null) {
            initializeNode(node);
        }
        return node;
    }

    public static RootNode findWithUuidLatestPublishedVersion(String uuid, Date today) {
        RootNode node = RootNode.find(
                "select distinct l from RootNode l " +
                        "where l.uuid = :uuid and " +
                        "(l.publish = null or l.publish < :today) and " +
                        "(l.unPublish = null OR l.unPublish >= :today)" +
                        "order by version desc"
        ).bind("uuid", uuid).bind("today", today).first();
        if (node != null) {
            initializeNode(node);
        }
        return node;
    }

    public static List<RootNode> findWithUuidAllVersions(String uuid) {
        List<RootNode> leaves = RootNode.find(
                "select distinct l from RootNode l where l.uuid = :uuid"
        ).bind("uuid", uuid).fetch();
        for (RootNode node : leaves) {
            initializeNode(node);
        }
        return leaves;
    }

    private static void initializeNode(RootNode node) {
        node.uiElements = new HashMap<String, List<play.modules.origo.core.ui.UIElement>>();
    }

    @Override
    public String toString() {
        return "Node (" + uuid + "," + version + ")";
    }
}

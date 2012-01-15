package models.cmscore;

import helpers.UIElementHelper;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "leafVersion", columnNames = {"uuid", "version"}))
public final class RootLeaf extends Model implements Leaf {

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
    private List<NavigationElement> navigationElements = new ArrayList<NavigationElement>();

    @Transient
    private Map<String, List<UIElement>> uiElements = new HashMap<String, List<UIElement>>();

    public RootLeaf(Long version) {
        this.uuid = UUID.randomUUID().toString();
        this.version = version;
    }

    public RootLeaf(String uuid, Long version) {
        this.uuid = uuid;
        this.version = version;
    }

    @Override
    public String getTitle() {
        return toString();
    }

    @Override
    public String getLeafId() {
        return uuid;
    }

    @Override
    public Long getLeafVersion() {
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

    public Class getTypeClass() {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find the class for leaf type [" + type + "]: " + e.getMessage(), e);
        }
    }

    @Override
    public String getThemeVariant() {
        return themeVariant;
    }

    @Override
    public Set<String> getRegions() {
        return this.uiElements.keySet();
    }

    @Override
    public List<NavigationElement> getNavigation() {
        return navigationElements;
    }

    @Override
    public NavigationElement addNavigation(NavigationElement navigationElement) {
        navigationElements.add(navigationElement);
        return navigationElement;
    }

    @Override
    public boolean removeNavigation(NavigationElement navigationElement) {
        return navigationElements.remove(navigationElement);
    }

    /* Interface methods */
    @Override
    public List<UIElement> getUIElements(String region) {
        return this.uiElements.get(region.toLowerCase());
    }

    @Override
    public UIElement addUIElement(UIElement uiElement) {
        return addUIElement(HEAD, uiElement, false);
    }

    @Override
    public UIElement addUIElement(String region, UIElement uiElement) {
        return addUIElement(region, uiElement, false);
    }

    @Override
    public UIElement addUIElement(String region, UIElement uiElement, boolean reorderElementsBelow) {
        String regionKey = region.toLowerCase();
        if (!uiElements.containsKey(regionKey)) {
            uiElements.put(regionKey, new ArrayList<UIElement>());
        }
        uiElements.get(regionKey).add(uiElement);
        if (reorderElementsBelow) {
            UIElementHelper.repositionUIElements(uiElements.get(regionKey), uiElement);
        }
        UIElementHelper.reorderUIElements(uiElements.get(regionKey));
        return uiElement;
    }

    @Override
    public boolean removeUIElement(String region, UIElement uiElement) {
        String regionKey = region.toLowerCase();
        if (uiElements.get(regionKey).remove(uiElement)) {
            UIElementHelper.reorderUIElements(uiElements.get(regionKey));
            return true;
        }
        return false;
    }

    public static List<RootLeaf> findAllCurrentVersions(Date today) {
        List<RootLeaf> leaves = RootLeaf.find(
                "select l from RootLeaf l " +
                        "where l.version = (" +
                        "select max(l2.version) from RootLeaf l2 " +
                        "where l2.uuid = l.uuid and " +
                        "(l2.publish = null or l2.publish < :today) and " +
                        "(l2.unPublish = null or l2.unPublish >= :today)" +
                        ")"
        ).bind("today", today).fetch();
        for (RootLeaf leaf : leaves) {
            initializeLeaf(leaf);
        }
        return leaves;
    }

    public static RootLeaf findWithUuidSpecificVersion(String uuid, Long version) {
        RootLeaf leaf = RootLeaf.find(
                "select distinct l from RootLeaf l " +
                        "where l.uuid = :uuid and " +
                        "l.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
        if (leaf != null) {
            initializeLeaf(leaf);
        }
        return leaf;
    }

    public static RootLeaf findWithUuidLatestPublishedVersion(String uuid, Date today) {
        RootLeaf leaf = RootLeaf.find(
                "select distinct l from RootLeaf l " +
                        "where l.uuid = :uuid and " +
                        "(l.publish = null or l.publish < :today) and " +
                        "(l.unPublish = null OR l.unPublish >= :today)" +
                        "order by version desc"
        ).bind("uuid", uuid).bind("today", today).first();
        if (leaf != null) {
            initializeLeaf(leaf);
        }
        return leaf;
    }

    public static List<RootLeaf> findWithUuidAllVersions(String uuid) {
        List<RootLeaf> leaves = RootLeaf.find(
                "select distinct l from RootLeaf l where l.uuid = :uuid"
        ).bind("uuid", uuid).fetch();
        for (RootLeaf leaf : leaves) {
            initializeLeaf(leaf);
        }
        return leaves;
    }

    private static void initializeLeaf(RootLeaf leaf) {
        leaf.uiElements = new HashMap<String, List<UIElement>>();
        leaf.navigationElements = new ArrayList<NavigationElement>();
    }

    @Override
    public String toString() {
        return "Leaf (" + uuid + "," + version + ")";
    }
}

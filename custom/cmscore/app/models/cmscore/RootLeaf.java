package models.cmscore;

import helpers.UIElementHelper;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "leafVersion", columnNames = {"uuid", "version"}))
public final class RootLeaf extends Model implements Leaf {

    @Required
    public String uuid;
    
    @Required
    public String title;

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
    private Map<String, List<UIElement>> uiElements = new WeakHashMap<String, List<UIElement>>();

    public RootLeaf(Long version, String title) {
        this.uuid = UUID.randomUUID().toString();
        this.title = title;
        this.version = version;
    }
    
    public RootLeaf(String uuid, Long version, String title) {
        this.uuid = uuid;
        this.title = title;
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public String getLeafId() {
        return uuid;
    }

    public Date getDatePublished() {
        return publish;
    }

    public Date getDateUnpublished() {
        return unPublish;
    }

    public Class getTypeClass() {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find the class for leaf type ["+type+"]: "+e.getMessage(), e);
        }
    }
    
    @Override
    public String getThemeVariant() {
        return themeVariant;
    }

    @Override
    public Set<String> getContentAreas() {
        return this.uiElements.keySet();
    }

    public void init() {
        this.uiElements = new HashMap<String, List<UIElement>>();
    }

    /* Interface methods */
    public List<UIElement> getUIElements(String contentArea) {
        return this.uiElements.get(contentArea);
    }

    public void addUIElement(String contentArea, UIElement uiElement) {
        addUIElement(contentArea, uiElement, false);
    }

    public void addUIElement(String contentArea, UIElement uiElement, boolean reorderElementsBelow) {
        if (!uiElements.containsKey(contentArea)) {
            uiElements.put(contentArea, new ArrayList<UIElement>());
        }
        uiElements.get(contentArea).add(uiElement);
        if(reorderElementsBelow){
            UIElementHelper.repositionUIElements(uiElements.get(contentArea), uiElement);
        }
        UIElementHelper.reorderUIElements(uiElements.get(contentArea));
    }

    public boolean removeUIElement(String contentArea, UIElement uiElement) {
        return uiElements.get(contentArea).remove(uiElement);
    }

    public static List<RootLeaf> findAllCurrentVersions(Date today) {
        return RootLeaf.find(
                "select l from RootLeaf l " +
                        "where l.version = (" +
                        "select max(l2.version) from RootLeaf l2 " +
                        "where l2.uuid = l.uuid and " +
                        "(l2.publish = null or l2.publish < :today) and " +
                        "(l2.unPublish = null or l2.unPublish >= :today)" +
                        ")"
        ).bind("today", today).fetch();
    }
    
    public static RootLeaf findWithUuidSpecificVersion(String uuid, Long version) {
        return RootLeaf.find(
                "select distinct l from RootLeaf l " +
                        "where l.uuid = :uuid and " +
                        "l.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
    }

    public static RootLeaf findWithUuidLatestPublishedVersion(String uuid, Date today) {
        return RootLeaf.find(
                "select distinct l from RootLeaf l " +
                        "where l.uuid = :uuid and " +
                        "(l.publish = null or l.publish < :today) and " +
                        "(l.unPublish = null OR l.unPublish >= :today)" +
                        "order by version desc"
        ).bind("uuid", uuid).bind("today", today).first();
    }

    public static List<RootLeaf> findWithUuidAllVersions(String uuid) {
        return RootLeaf.find(
                "select distinct l from RootLeaf l " +
                        "where l.uuid = :uuid"
        ).bind("uuid", uuid).fetch();
    }

    public String toString() {
        return "Leaf (" + uuid + "," + version + ") - " + title;
    }
}

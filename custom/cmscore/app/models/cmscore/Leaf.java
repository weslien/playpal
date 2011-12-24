package models.cmscore;

import helpers.UIElementHelper;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "leafVersion", columnNames = {"uuid", "version"}))
public final class Leaf extends Model implements LeafType {

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

    public Class type;
    
    public String themeVariant;

    @Transient
    private Map<String, List<UIElement>> uiElements = new WeakHashMap<String, List<UIElement>>();

    public Leaf(Long version, String title) {
        this.uuid = UUID.randomUUID().toString();
        this.title = title;
        this.version = version;
    }
    
    public Leaf(String uuid, Long version, String title) {
        this.uuid = uuid;
        this.title = title;
        this.version = version;
    }

    // This is a hack to make yaml work. Maybe need to figure out a different way to do this
    public void setType(String typeAsString) throws ClassNotFoundException {
        type = Class.forName(typeAsString);
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

    @Override
    public String getThemeVariant() {
        return themeVariant;
    }

    @Override
    public Set<String> getContentAreas() {
        return this.uiElements.keySet();
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

    public static List<Leaf> findAllCurrentVersions(Date today) {
        return Leaf.find(
                "select l from Leaf l " +
                "where l.version = (" +
                        "select max(l2.version) from Leaf l2 " +
                        "where l2.uuid = l.uuid and " +
                        "(l2.publish = null or l2.publish < :today) and " +
                        "(l2.unPublish = null OR l2.unPublish >= :today)" +
                ")"
        ).bind("today", today).fetch();
    }
    
    public static Leaf findWithUuidSpecificVersion(String uuid, Long version) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uuid and " +
                "l.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
    }

    public static Leaf findWithUuidLatestPublishedVersion(String uuid, Date today) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uuid and " +
                "(l.publish = null or l.publish < :today) and " +
                "(l.unPublish = null OR l.unPublish >= :today)" +
                "order by version desc"
        ).bind("uuid", uuid).bind("today", today).first();
    }

    public static List<Leaf> findWithUuidAllVersions(String uuid) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uuid"
        ).bind("uuid", uuid).fetch();
    }

    public String toString() {
        return "Leaf (" + uuid + "," + version + ") - " + title;
    }
}

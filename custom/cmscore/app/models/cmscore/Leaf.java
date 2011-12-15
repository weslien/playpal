package models.cmscore;

import helpers.UIElementHelper;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "leafVersion", columnNames = {"uuid", "version"}))
public class Leaf extends Model implements LeafType {

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

    @Transient
    private List<UIElement> uiElements = new ArrayList<UIElement>();

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

    @Override
    public List<UIElement> getUIElements() {
        return this.uiElements;
    }

    @Override
    public void addUIElement(UIElement uiElement) {
        addUIElement(uiElement, false);
    }

    @Override
    public void addUIElement(UIElement uiElement, boolean reorderElementsBelow) {
        this.uiElements.add(uiElement);
        if(reorderElementsBelow){
            UIElementHelper.repositionUIElements(this.uiElements, uiElement);
        }
        UIElementHelper.reorderUIElements(this.uiElements);
    }

    @Override
    public boolean removeUIElement(UIElement uiElement) {
        return this.uiElements.remove(uiElement);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUniqueId() {
        return uuid;
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
    public String render() {
        StringBuilder buf = new StringBuilder();
        buf.append("Leaf:");
        buf.append(getUniqueId());
        buf.append("<br/>");
        buf.append(getTitle());
        buf.append("<br/>");
        buf.append("UIElements");
        buf.append("<div id=\""+getUniqueId()+"\">");
        for(UIElement elem: this.uiElements){
            buf.append("ID:");
            buf.append(elem.getId());
            buf.append(" (");
            buf.append(elem.getWeight());
            buf.append(")");
            buf.append("<br/>");
            buf.append(elem.getTitle());
            buf.append("<br/>");
            buf.append(elem.getBody());
            buf.append("<hr/>");
        }
        buf.append("</div>");

        return buf.toString();
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
        return "(" + uuid + "," + version + ") - " +title;
    }
}

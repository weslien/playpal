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

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "pageVersion", columnNames = {"parentUuid", "parentVersion"}))
public class Page extends Model implements LeafType {

    @Required
    @Column(name = "parentUuid")
    public String uuid;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    @Column(name = "parentVersion")
    public Long version;

    @Required
    public String body;

    @Transient
    public Leaf leaf;

    @Transient
    private List<UIElement> uiElements = new ArrayList<UIElement>();

    /* Interface methods */

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
        return this.leaf.title;
    }

    @Override
    public String getUniqueId() {
        return this.uuid;
    }

    @Override
    public Date getDatePublished() {
        return this.leaf.publish;
    }

    @Override
    public Date getDateUnpublished() {
        return this.leaf.unPublish;
    }

    @Override
    public String render() {
        StringBuilder buf = new StringBuilder();
        buf.append("ID:");
        buf.append(getUniqueId());
        buf.append("<br/>");
        buf.append(getTitle());
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
        buf.append(body);
        buf.append("<hr/>");

        return buf.toString();
    }

    public static Page findWithUuidSpecificVersion(String uuid, Long version) {
        return Leaf.find(
                "select distinct p from Page p " +
                        "where p.uuid = :uuid and " +
                        "p.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
    }

}

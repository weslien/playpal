package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The basic type for a page. Directly linked to a Leaf, both it's version and id.
 * @see LeafType
 * @see Leaf
 * @see listeners.PageListener
 */
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

    @Transient
    public Leaf leaf;

    @Override
    public String getLeafId() {
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
    public String getTitle() {
        return leaf.title;
    }

    @Override
    public String getThemeVariant() {
        return leaf.themeVariant;
    }

    /* Interface methods */

    @Override
    public Set<String> getContentAreas() {
        return leaf.getContentAreas();
    }

    @Override
    public List<UIElement> getUIElements(String contentArea) {
        return leaf.getUIElements(contentArea);
    }

    @Override
    public void addUIElement(String contentArea, UIElement uiElement) {
        leaf.addUIElement(contentArea, uiElement, false);
    }

    @Override
    public void addUIElement(String contentArea, UIElement uiElement, boolean reorderElementsBelow) {
        leaf.addUIElement(contentArea, uiElement, reorderElementsBelow);
    }

    @Override
    public boolean removeUIElement(String contentArea, UIElement uiElement) {
        return leaf.removeUIElement(contentArea, uiElement);
    }

    @Override
    public String toString() {
        return "Page (" + uuid + "," + version + ") - " + leaf.getTitle();
    }

    public static Page findWithUuidSpecificVersion(String uuid, Long version) {
        return Leaf.find(
                "select distinct p from Page p " +
                        "where p.uuid = :uuid and " +
                        "p.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
    }

}


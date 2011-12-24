package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The basic type for a page. Directly linked to a RootLeaf, both it's version and id.
 * @see play.modules.cmscore.Leaf
 * @see RootLeaf
 * @see listeners.PageListener
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "pageVersion", columnNames = {"parentUuid", "parentVersion"}))
public class Page extends Model implements Leaf {

    @Required
    @Column(name = "parentUuid")
    public String uuid;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    @Column(name = "parentVersion")
    public Long version;

    @Transient
    public RootLeaf rootLeaf;

    @Override
    public String getLeafId() {
        return this.uuid;
    }

    @Override
    public Date getDatePublished() {
        return this.rootLeaf.publish;
    }

    @Override
    public Date getDateUnpublished() {
        return this.rootLeaf.unPublish;
    }

    @Override
    public String getTitle() {
        return rootLeaf.title;
    }

    @Override
    public String getThemeVariant() {
        return rootLeaf.themeVariant;
    }

    /* Interface methods */

    @Override
    public Set<String> getContentAreas() {
        return rootLeaf.getContentAreas();
    }

    @Override
    public List<UIElement> getUIElements(String contentArea) {
        return rootLeaf.getUIElements(contentArea);
    }

    @Override
    public void addUIElement(String contentArea, UIElement uiElement) {
        rootLeaf.addUIElement(contentArea, uiElement, false);
    }

    @Override
    public void addUIElement(String contentArea, UIElement uiElement, boolean reorderElementsBelow) {
        rootLeaf.addUIElement(contentArea, uiElement, reorderElementsBelow);
    }

    @Override
    public boolean removeUIElement(String contentArea, UIElement uiElement) {
        return rootLeaf.removeUIElement(contentArea, uiElement);
    }

    @Override
    public String toString() {
        return "Page (" + uuid + "," + version + ") - " + rootLeaf.getTitle();
    }

    public static Page findWithUuidSpecificVersion(String uuid, Long version) {
        return RootLeaf.find(
                "select distinct p from Page p " +
                        "where p.uuid = :uuid and " +
                        "p.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
    }

}


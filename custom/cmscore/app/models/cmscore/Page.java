package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.Node;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The basic type for a page. Directly linked to a RootNode, both it's version and id.
 *
 * @see play.modules.cmscore.Node
 * @see RootNode
 * @see listeners.PageListener
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "pageVersion", columnNames = {"parentUuid", "parentVersion"}))
public class Page extends Model implements Node {

    @Required
    @Column(name = "parentUuid")
    public String uuid;

    // TODO: Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3 (2.0?)
    @Required
    @Column(name = "parentVersion")
    public Long version;

    @Transient
    public RootNode rootNode;

    @Required
    public String title;

    @Override
    public String getNodeId() {
        return this.uuid;
    }

    @Override
    public Long getVersion() {
        return this.version;
    }

    @Override
    public Date getDatePublished() {
        return this.rootNode.publish;
    }

    @Override
    public Date getDateUnpublished() {
        return this.rootNode.unPublish;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getThemeVariant() {
        return rootNode.themeVariant;
    }

    @Override
    public Set<String> getRegions() {
        return rootNode.getRegions();
    }

    @Override
    public List<UIElement> getUIElements(String region) {
        return rootNode.getUIElements(region);
    }

    @Override
    public UIElement addUIElement(UIElement uiElement) {
        return rootNode.addUIElement(HEAD, uiElement, false);
    }

    @Override
    public UIElement addUIElement(String region, UIElement uiElement) {
        return rootNode.addUIElement(region, uiElement, false);
    }

    @Override
    public UIElement addUIElement(String region, UIElement uiElement, boolean reorderElementsBelow) {
        return rootNode.addUIElement(region, uiElement, reorderElementsBelow);
    }

    @Override
    public boolean removeUIElement(String region, UIElement uiElement) {
        return rootNode.removeUIElement(region, uiElement);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Page (")
                .append(uuid).append(",")
                .append(version).append(") - ")
                .append(title)
                .toString();
    }

    public static List<Page> findAllCurrentVersions(Date asOfDate) {
        return Page.
                find(
                        "select p from Page p " +
                                "where p.id in (" +
                                "select l.id from RootNode l " +
                                "where l.version = (" +
                                "select max(l2.version) from RootNode l2 " +
                                "where l2.uuid = l.uuid and " +
                                "(l2.publish = null or l2.publish < :today) and " +
                                "(l2.unPublish = null or l2.unPublish >= :today)" +
                                ")" +
                                ")").
                bind("today", asOfDate).
                fetch();
    }

    public static Page findCurrentVersion(String uuid, Date asOfDate) {
        return Page.
                find(
                        "select p from Page p " +
                                "where p.uuid = :uuid and p.id in (" +
                                "select l.id from RootNode l " +
                                "where l.version = (" +
                                "select max(l2.version) from RootNode l2 " +
                                "where l2.uuid = l.uuid and " +
                                "(l2.publish = null or l2.publish < :today) and " +
                                "(l2.unPublish = null or l2.unPublish >= :today)" +
                                ")" +
                                ")").
                bind("uuid", uuid).
                bind("today", asOfDate).
                first();
    }

    public static Page findWithUuidSpecificVersion(String uuid, Long version) {
        return RootNode.
                find(
                        "select distinct p from Page p " +
                                "where p.uuid = :uuid and " +
                                "p.version = :version").
                bind("uuid", uuid).
                bind("version", version).
                first();
    }

}


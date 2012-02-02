package models.origo.core;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The basic type for a page. Directly linked to a RootNode, both it's version and id.
 *
 * @see play.modules.origo.core.Node
 * @see RootNode
 * @see origo.listeners.BasicPageProvider
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "pageVersion", columnNames = {"parentNodeId", "parentVersion"}))
public class BasicPage extends Model implements Node {

    @Required
    @Column(name = "parentNodeId")
    public String nodeId;

    // TODO: Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3 (2.0?)
    @Required
    @Column(name = "parentVersion")
    public Long version;

    @Transient
    public RootNode rootNode;

    @Required
    public String title;

    @Required
    public String leadReferenceId;

    @Required
    public String bodyReferenceId;

    @Override
    public String getNodeId() {
        return this.nodeId;
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

    public String getLeadReferenceId() {
        return leadReferenceId;
    }

    public void setLeadReferenceId(String leadReferenceId) {
        this.leadReferenceId = leadReferenceId;
    }

    public String getBodyReferenceId() {
        return bodyReferenceId;
    }

    public void setBodyReferenceId(String bodyReferenceId) {
        this.bodyReferenceId = bodyReferenceId;
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
    public UIElement addHeadUIElement(UIElement uiElement) {
        return rootNode.addHeadUIElement(uiElement);
    }

    @Override
    public UIElement addUIElement(UIElement uiElement) {
        return rootNode.addUIElement(uiElement, false);
    }

    @Override
    public UIElement addHeadUIElement(UIElement uiElement, boolean reorderElementsBelow) {
        return rootNode.addHeadUIElement(uiElement, reorderElementsBelow);
    }

    @Override
    public UIElement addUIElement(UIElement uiElement, boolean reorderElementsBelow) {
        return rootNode.addUIElement(uiElement, reorderElementsBelow);
    }

    @Override
    public boolean removeHeadUIElement(UIElement uiElement) {
        return rootNode.removeHeadUIElement(uiElement);
    }

    @Override
    public boolean removeUIElement(UIElement uiElement) {
        return rootNode.removeUIElement(uiElement);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("BasicPage {").
                append("nodeId='").append(nodeId).append("\', ").
                append("version=").append(version).
                append("rootNode=").append(rootNode).
                append("title='").append(title).append("\', ").
                append("leadReferenceId='").append(leadReferenceId).append("\', ").
                append("bodyReferenceId='").append(bodyReferenceId).append("\', ").
                append('}').toString();
    }

    public static List<BasicPage> findAllCurrentVersions(Date asOfDate) {
        return BasicPage.
                find(
                        "select p from BasicPage p " +
                                "where p.id in (" +
                                "select l.id from RootNode l " +
                                "where l.version = (" +
                                "select max(l2.version) from RootNode l2 " +
                                "where l2.nodeId = l.nodeId and " +
                                "(l2.publish = null or l2.publish < :today) and " +
                                "(l2.unPublish = null or l2.unPublish >= :today)" +
                                ")" +
                                ")").
                bind("today", asOfDate).
                fetch();
    }

    public static BasicPage findCurrentVersion(String nodeId, Date asOfDate) {
        return BasicPage.
                find(
                        "select p from BasicPage p " +
                                "where p.nodeId = :nodeId and p.id in (" +
                                "select l.id from RootNode l " +
                                "where l.version = (" +
                                "select max(l2.version) from RootNode l2 " +
                                "where l2.nodeId = l.nodeId and " +
                                "(l2.publish = null or l2.publish < :today) and " +
                                "(l2.unPublish = null or l2.unPublish >= :today)" +
                                ")" +
                                ")").
                bind("nodeId", nodeId).
                bind("today", asOfDate).
                first();
    }

    public static BasicPage findLatestVersion(String nodeId) {
        return BasicPage.
                find(
                        "select p from BasicPage p " +
                                "where p.nodeId = :nodeId and p.id in (" +
                                "select l.id from RootNode l " +
                                "where l.version = (" +
                                "select max(l2.version) from RootNode l2 " +
                                "where l2.nodeId = l.nodeId" +
                                ")" +
                                ")").
                bind("nodeId", nodeId).
                first();
    }

    public static BasicPage findWithNodeIdAndSpecificVersion(String nodeId, Long version) {
        return RootNode.
                find(
                        "select distinct p from BasicPage p " +
                                "where p.nodeId = :nodeId and " +
                                "p.version = :version").
                bind("nodeId", nodeId).
                bind("version", version).
                first();
    }

    public static List<BasicPage> findAllLatestVersions() {
        return BasicPage.
                find(
                        "select p from BasicPage p " +
                                "where p.id in (" +
                                "select l.id from RootNode l " +
                                "where l.version = (" +
                                "select max(l2.version) from RootNode l2 " +
                                "where l2.nodeId = l.nodeId" +
                                ")" +
                                ")").
                fetch();
    }
}


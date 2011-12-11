package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.LeafType;
import play.modules.cmscore.ui.UIElement;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gustav
 * Date: 2011-12-11
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Page extends Model implements LeafType {

    @Transient
    private Leaf leaf;
    
    private List<UIElement> uiElements = new ArrayList<UIElement>();

    @Required
    public String uuid;

    @Required
    public String leafUuid;

    @Required
    public String body;


    /* Interface methods */

    @Override
    public void setLeaf(final Leaf leaf) {
        this.leaf = leaf;
    }

    @Override
    public List<UIElement> getUIElements() {
        return this.uiElements;
    }

    @Override
    public void addUIElement(UIElement uiElement) {
        addUIElement(uiElement, false);
    }

    private void reorderUIElements() {
        Collections.sort(this.uiElements, new Comparator<UIElement>() {
            @Override
            public int compare(UIElement uiElement, UIElement uiElement1) {
                return (uiElement.getWeight() >= uiElement1.getWeight()) ? 1 : 0;
            }
        });
    }

    @Override
    public void addUIElement(UIElement uiElement, boolean reorderElementsBelow) {
        this.uiElements.add(uiElement);
        if(reorderElementsBelow){
            repositionUIElements(uiElement);
        }
        reorderUIElements();
    }

    private void repositionUIElements(UIElement uiElement) {
        for(UIElement elem : this.uiElements){
            if(elem.getWeight() >= uiElement.getWeight()){
                elem.setWeight(elem.getWeight()+1);
            }
        }
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
        StringBuffer buf = new StringBuffer();
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

        return buf.toString();
        
    }
}

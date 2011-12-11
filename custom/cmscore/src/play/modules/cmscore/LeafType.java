package play.modules.cmscore;

import models.cmscore.Leaf;
import play.modules.cmscore.ui.UIElement;

import java.util.Date;
import java.util.List;

/**
 * Holds
 * User: gustav
 * Date: 2011-12-11
 * Time: 19:42
 * To change this template use File | Settings | File Templates.
 */
public interface LeafType {

    void setLeaf(final Leaf leaf);

    List<UIElement> getUIElements();

    void addUIElement(final UIElement uiElement);

    void addUIElement(final UIElement uiElement, final boolean reorderElementsBelow);

    boolean removeUIElement(final UIElement uiElement);

    String getTitle();

    String getUniqueId();

    Date getDatePublished();

    Date getDateUnpublished();

    String render();


}

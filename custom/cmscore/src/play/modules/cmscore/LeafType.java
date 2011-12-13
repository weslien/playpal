package play.modules.cmscore;

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

    List<UIElement> getUIElements();

    void addUIElement(UIElement uiElement);

    void addUIElement(UIElement uiElement, boolean reorderElementsBelow);

    boolean removeUIElement(UIElement uiElement);

    String getTitle();

    String getUniqueId();

    Date getDatePublished();

    Date getDateUnpublished();

    String render();

}

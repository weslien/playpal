package play.modules.cmscore;

import play.modules.cmscore.ui.UIElement;

import java.util.Date;
import java.util.List;

public interface LeafType {

    List<UIElement> getUIElements();

    void addUIElement(UIElement uiElement);

    void addUIElement(UIElement uiElement, boolean reorderElementsBelow);

    boolean removeUIElement(UIElement uiElement);

    String getTitle();

    String getUniqueId();

    Date getDatePublished();

    Date getDateUnpublished();

    String getTemplate();

}

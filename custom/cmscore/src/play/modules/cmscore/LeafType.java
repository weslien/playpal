package play.modules.cmscore;

import play.modules.cmscore.ui.UIElement;

import java.util.Date;
import java.util.List;

/**
 * An interface that each module/add-ons/plugin should implement if it adds a type with a \@Provides annotation.
 * While the modules/add-ons/plugins handle a leaf and modifiy it it will be of this type. When rendering starts this will be turned into a RenderedLeaf.
 * @see play.modules.cmscore.annotations.Provides
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

    String getTemplate();

}

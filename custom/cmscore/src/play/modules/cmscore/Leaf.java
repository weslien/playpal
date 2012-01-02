package play.modules.cmscore;

import play.modules.cmscore.ui.UIElement;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * An interface that each module/add-ons/plugin should implement if it adds a type with a \@Provides annotation.
 * While the modules/add-ons/plugins handle a rootLeaf and modifiy it it will be of this type. When rendering starts this will be turned into a RenderedLeaf.
 * @see play.modules.cmscore.annotations.Provides
 */
public interface Leaf {

    String getLeafId();

    String getTitle();

    Date getDatePublished();

    Date getDateUnpublished();

    String getThemeVariant();

    Set<String> getRegions();
    
    List<UIElement> getUIElements(String region);

    void addUIElement(String region, UIElement uiElement);

    void addUIElement(String region, UIElement uiElement, boolean reorderElementsBelow);

    boolean removeUIElement(String region, UIElement uiElement);

}

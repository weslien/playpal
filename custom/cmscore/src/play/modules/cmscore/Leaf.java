package play.modules.cmscore;

import play.modules.cmscore.ui.UIElement;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * An interface that each module/add-ons/plugin should implement if it adds a type with a \@Provides annotation.
 * While the modules/add-ons/plugins handle a rootLeaf and modifiy it it will be of this type. When rendering starts this will be turned into a RenderedLeaf.
 * @see play.modules.cmscore.annotations.Provides
 * @see play.modules.cmscore.annotations.ThemeVariant
 * @see UIElement
 */
public interface Leaf {

    public static final String HEAD = "head";

    /**
     * The uuid of the leaf
     * @return a unique key for this leaf
     */
    String getLeafId();

    /**
     * The version of this leaf
     * @return a version number
     */
    Long getLeafVersion();

    /**
     * A title for this leaf
     * @return a title
     */
    String getTitle();

    /**
     * The date this version should be available for public viewing
     * @return a date
     */
    Date getDatePublished();

    /**
     * The date this version should be removed from public viewing
     * @return a date
     */
    Date getDateUnpublished();

    /**
     * The unique name of the theme variant used for this page
     * @return theme variant name
     * @see play.modules.cmscore.annotations.ThemeVariant
     */
    String getThemeVariant();

    /**
     * All the available regions stored on this leaf.
     * @return a set of region names that can be used for showing content
     */
    Set<String> getRegions();

    /**
     * A collection of UIElements that should be rendered on the screen. Regions are determined by the theme variant used.
     * @param region the area of the screen where this element should be rendered
     * @return all uiElements for the region
     */
    List<UIElement> getUIElements(String region);

    /**
     * Add an element that should be rendered in the HEAD of the page. Regions are determined by the theme variant used.
     * @param uiElement the element to be rendered
     * @see play.modules.cmscore.annotations.ThemeVariant
     * @return the newly added UIElement
     */
    UIElement addUIElement(UIElement uiElement);

    /**
     * Add an element that should be rendered on the page. Regions are determined by the theme variant used.
     * @param region the area of the screen where this element should be rendered
     * @param uiElement the element to be rendered
     * @see play.modules.cmscore.annotations.ThemeVariant
     * @return the newly added UIElement
     */
    UIElement addUIElement(String region, UIElement uiElement);

    /**
     * Add an element that should be rendered on the page. Regions are determined by the theme variant used.
     * @param region the area of the screen where this element should be rendered
     * @param uiElement the element to be rendered
     * @param reorderElementsBelow if true then all elements below this new element will be reordered according to their individual weight
     * @see play.modules.cmscore.annotations.ThemeVariant
     * @return the newly added UIElement
     */
    UIElement addUIElement(String region, UIElement uiElement, boolean reorderElementsBelow);

    /**
     * Removes an element so it is not rendered. Will force a reordering of all elements below.
     * @param region the area of the screen where this element should be rendered
     * @param uiElement the element to be rendered
     * @return if an object matching the region and the uiElement could be found and removed
     */
    boolean removeUIElement(String region, UIElement uiElement);

}

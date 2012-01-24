package play.modules.origo.core;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * An interface that each module/add-on/plugin should implement if it adds a type with a \@Provides annotation.
 * While the modules/add-ons/plugins handle a rootNode and modify it, it will be of this type. When rendering
 * starts this will be turned into a RenderedNode.
 *
 * @see play.modules.origo.core.annotations.Provides
 * @see play.modules.origo.core.annotations.ThemeVariant
 * @see play.modules.origo.core.ui.UIElement
 */
public interface Node {

    static final String HEAD = "head";

    /**
     * The uuid of the node
     *
     * @return a unique key for this node
     */
    String getNodeId();

    /**
     * The version of this node
     *
     * @return a version number
     */
    Long getVersion();

    /**
     * A title for this node
     *
     * @return a title
     */
    String getTitle();

    /**
     * The date this version should be available for public viewing
     *
     * @return a date
     */
    Date getDatePublished();

    /**
     * The date this version should be removed from public viewing
     *
     * @return a date
     */
    Date getDateUnpublished();

    /**
     * The unique name of the theme variant used for this page
     *
     * @return theme variant name
     * @see play.modules.origo.core.annotations.ThemeVariant
     */
    String getThemeVariant();

    /**
     * All the available regions stored on this node.
     *
     * @return a set of region names that can be used for showing content
     * @see play.modules.origo.core.annotations.ThemeVariant
     */
    Set<String> getRegions();

    /**
     * A collection of UIElements that should be rendered on the screen. Regions are determined by the theme variant used.
     *
     * @param region the area of the screen where this element should be rendered
     * @return all uiElements for the region
     */
    List<play.modules.origo.core.ui.UIElement> getUIElements(String region);

    /**
     * Add an element that should be rendered in the HEAD of the page. Regions are determined by the theme variant used.
     *
     * @param uiElement the element to be rendered
     * @return the newly added UIElement
     * @see play.modules.origo.core.annotations.ThemeVariant
     */
    play.modules.origo.core.ui.UIElement addUIElement(play.modules.origo.core.ui.UIElement uiElement);

    /**
     * Add an element that should be rendered on the page. Regions are determined by the theme variant used.
     *
     * @param region    the area of the screen where this element should be rendered
     * @param uiElement the element to be rendered
     * @return the newly added UIElement
     * @see play.modules.origo.core.annotations.ThemeVariant
     */
    play.modules.origo.core.ui.UIElement addUIElement(String region, play.modules.origo.core.ui.UIElement uiElement);

    /**
     * Add an element that should be rendered on the page. Regions are determined by the theme variant used.
     *
     * @param region               the area of the screen where this element should be rendered
     * @param uiElement            the element to be rendered
     * @param reorderElementsBelow if true then all elements below this new element will be reordered according to their individual weight
     * @return the newly added UIElement
     * @see play.modules.origo.core.annotations.ThemeVariant
     */
    play.modules.origo.core.ui.UIElement addUIElement(String region, play.modules.origo.core.ui.UIElement uiElement, boolean reorderElementsBelow);

    /**
     * Removes an element so it is not rendered. Will force a reordering of all elements below.
     *
     * @param region    the area of the screen where this element should be rendered
     * @param uiElement the element to be rendered
     * @return if an object matching the region and the uiElement could be found and removed
     */
    boolean removeUIElement(String region, play.modules.origo.core.ui.UIElement uiElement);

}

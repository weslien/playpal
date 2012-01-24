package origo.helpers;

import models.origo.core.RootNode;
import models.origo.core.Segment;
import org.apache.commons.lang.StringUtils;
import origo.listeners.PageNotFoundException;
import play.modules.origo.core.Node;

import java.util.Collection;
import java.util.Date;

public class NodeHelper {

    public static play.modules.origo.core.Node load(String uuid) {
        //Load RootNode model
        RootNode rootNode = RootNode.findWithUuidLatestPublishedVersion(uuid, new Date());
        if (rootNode == null) {
            throw new PageNotFoundException(uuid);
        }

        return load(rootNode);
    }

    public static play.modules.origo.core.Node load(String uuid, long version) {
        //Load RootNode model
        RootNode rootNode = RootNode.findWithUuidSpecificVersion(uuid, version);
        if (rootNode == null) {
            throw new PageNotFoundException(uuid);
        }

        return load(rootNode);
    }

    private static Node load(RootNode rootNode) {
        boolean hasType = !StringUtils.isBlank(rootNode.type) && !rootNode.type.equals(RootNode.class.getName());
        if (hasType) {
            triggerBeforeNodeLoaded(rootNode.type, rootNode);
        }

        play.modules.origo.core.Node node = rootNode;
        if (hasType) {
            node = triggerProvidesNodeListener(rootNode.type, rootNode);
        }

        if (hasType) {
            triggerAfterNodeLoaded(rootNode.type, node);
        }

        addSegments(node);

        return node;
    }

    private static void addSegments(play.modules.origo.core.Node node) {
        Collection<Segment> segments = Segment.findWithUuidSpecificVersion(node.getNodeId(), node.getVersion());
        for (Segment segment : segments) {
            triggerBeforeSegmentLoaded(segment.type, node, segment);
            play.modules.origo.core.ui.UIElement uiElement = triggerProvidesSegmentListener(segment.type, node, segment);
            if (uiElement != null) {
                triggerAfterSegmentLoaded(segment.type, node, segment, uiElement);
                node.addUIElement(segment.region, uiElement);
            }
        }
    }

    /*
     * Convenience methods for hooks with SEGMENT type
     */
    public static play.modules.origo.core.ui.UIElement triggerProvidesSegmentListener(String withType, Node node, Segment segment) {
        return ProvidesHelper.triggerListener(play.modules.origo.core.annotations.Provides.Type.SEGMENT, withType, node, Segment.class, segment);
    }

    private static void triggerBeforeSegmentLoaded(String nodeType, play.modules.origo.core.Node node, Segment segment) {
        OnLoadHelper.triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type.SEGMENT, nodeType, node, Segment.class, segment);
    }

    private static void triggerAfterSegmentLoaded(String withType, play.modules.origo.core.Node node, Segment segment, play.modules.origo.core.ui.UIElement uiElement) {
        OnLoadHelper.triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type.SEGMENT, withType, node, Segment.class, segment, uiElement);
    }

    /*
     * Convenience methods for hooks with NODE type
     */
    public static Node triggerProvidesNodeListener(String withType, RootNode rootNode) {
        return ProvidesHelper.triggerListener(play.modules.origo.core.annotations.Provides.Type.NODE, withType, rootNode);
    }

    public static void triggerBeforeNodeLoaded(String withType, RootNode rootNode) {
        OnLoadHelper.triggerBeforeListener(play.modules.origo.core.annotations.OnLoad.Type.NODE, withType, rootNode);
    }

    public static void triggerAfterNodeLoaded(String withType, Node node) {
        OnLoadHelper.triggerAfterListener(play.modules.origo.core.annotations.OnLoad.Type.NODE, withType, node);
    }

}

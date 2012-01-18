package helpers;

import listeners.PageNotFoundException;
import models.cmscore.RootNode;
import models.cmscore.Segment;
import play.modules.cmscore.Node;
import play.modules.cmscore.annotations.OnLoad;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.ui.UIElement;

import java.util.Collection;
import java.util.Date;

public class NodeHelper {

    public static Node load(String uuid) {
        //Load RootNode model
        RootNode rootNode = RootNode.findWithUuidLatestPublishedVersion(uuid, new Date());
        if (rootNode == null) {
            throw new PageNotFoundException(uuid);
        }

        return load(rootNode);
    }

    public static Node load(String uuid, long version) {
        //Load RootNode model
        RootNode rootNode = RootNode.findWithUuidSpecificVersion(uuid, version);
        if (rootNode == null) {
            throw new PageNotFoundException(uuid);
        }

        return load(rootNode);
    }

    private static Node load(RootNode rootNode) {
        boolean hasType = rootNode.type != null && !rootNode.getTypeClass().equals(RootNode.class);
        if (hasType) {
            triggerBeforeNodeLoaded(rootNode.getTypeClass(), rootNode);
        }

        Node node = rootNode;
        if (hasType) {
            node = triggerProvidesNodeListener(rootNode.getTypeClass(), rootNode);
        }

        if (hasType) {
            triggerAfterNodeLoaded(rootNode.getTypeClass(), node);
        }

        addSegments(node);

        return node;
    }

    private static void addSegments(Node node) {
        Collection<Segment> segments = Segment.findWithUuidSpecificVersion(node.getNodeId(), node.getVersion());
        for (Segment segment : segments) {
            triggerBeforeSegmentLoaded(segment.getTypeClass(), node, segment);
            UIElement uiElement = triggerProvidesSegmentListener(segment.getTypeClass(), node, segment);
            if (uiElement != null) {
                triggerAfterSegmentLoaded(segment.getTypeClass(), node, segment, uiElement);
                node.addUIElement(segment.region, uiElement);
            }
        }
    }

    /*
     * Convenience methods for hooks with SEGMENT type
     */
    public static UIElement triggerProvidesSegmentListener(Class withType, Node node, Segment segment) {
        return ProvidesHelper.triggerListener(Provides.Type.SEGMENT, withType, node, Segment.class, segment);
    }

    private static void triggerBeforeSegmentLoaded(Class nodeType, Node node, Segment segment) {
        OnLoadHelper.triggerBeforeListener(OnLoad.Type.SEGMENT, nodeType, node, Segment.class, segment);
    }

    private static void triggerAfterSegmentLoaded(Class nodeType, Node node, Segment segment, UIElement uiElement) {
        OnLoadHelper.triggerAfterListener(OnLoad.Type.SEGMENT, nodeType, node, Segment.class, segment, uiElement);
    }

    /*
     * Convenience methods for hooks with NODE type
     */
    public static Node triggerProvidesNodeListener(Class withType, RootNode rootNode) {
        return ProvidesHelper.triggerListener(Provides.Type.NODE, withType, rootNode);
    }

    public static void triggerBeforeNodeLoaded(Class withType, RootNode rootNode) {
        OnLoadHelper.triggerBeforeListener(OnLoad.Type.NODE, withType, rootNode);
    }

    public static void triggerAfterNodeLoaded(Class withType, Node node) {
        OnLoadHelper.triggerAfterListener(OnLoad.Type.NODE, withType, node);
    }

}

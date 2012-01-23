package helpers;

import listeners.PageNotFoundException;
import models.cmscore.RootNode;
import models.cmscore.Segment;
import org.apache.commons.lang.StringUtils;
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
        boolean hasType = !StringUtils.isBlank(rootNode.type) && !rootNode.type.equals(RootNode.class.getName());
        if (hasType) {
            triggerBeforeNodeLoaded(rootNode.type, rootNode);
        }

        Node node = rootNode;
        if (hasType) {
            node = triggerProvidesNodeListener(rootNode.type, rootNode);
        }

        if (hasType) {
            triggerAfterNodeLoaded(rootNode.type, node);
        }

        addSegments(node);

        return node;
    }

    private static void addSegments(Node node) {
        Collection<Segment> segments = Segment.findWithUuidSpecificVersion(node.getNodeId(), node.getVersion());
        for (Segment segment : segments) {
            triggerBeforeSegmentLoaded(segment.type, node, segment);
            UIElement uiElement = triggerProvidesSegmentListener(segment.type, node, segment);
            if (uiElement != null) {
                triggerAfterSegmentLoaded(segment.type, node, segment, uiElement);
                node.addUIElement(segment.region, uiElement);
            }
        }
    }

    /*
     * Convenience methods for hooks with SEGMENT type
     */
    public static UIElement triggerProvidesSegmentListener(String withType, Node node, Segment segment) {
        return ProvidesHelper.triggerListener(Provides.Type.SEGMENT, withType, node, Segment.class, segment);
    }

    private static void triggerBeforeSegmentLoaded(String nodeType, Node node, Segment segment) {
        OnLoadHelper.triggerBeforeListener(OnLoad.Type.SEGMENT, nodeType, node, Segment.class, segment);
    }

    private static void triggerAfterSegmentLoaded(String withType, Node node, Segment segment, UIElement uiElement) {
        OnLoadHelper.triggerAfterListener(OnLoad.Type.SEGMENT, withType, node, Segment.class, segment, uiElement);
    }

    /*
     * Convenience methods for hooks with NODE type
     */
    public static Node triggerProvidesNodeListener(String withType, RootNode rootNode) {
        return ProvidesHelper.triggerListener(Provides.Type.NODE, withType, rootNode);
    }

    public static void triggerBeforeNodeLoaded(String withType, RootNode rootNode) {
        OnLoadHelper.triggerBeforeListener(OnLoad.Type.NODE, withType, rootNode);
    }

    public static void triggerAfterNodeLoaded(String withType, Node node) {
        OnLoadHelper.triggerAfterListener(OnLoad.Type.NODE, withType, node);
    }

}

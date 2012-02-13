package origo.helpers;

import models.origo.core.RootNode;
import org.apache.commons.lang.StringUtils;
import origo.listeners.PageNotFoundException;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.Types;

import java.util.Date;

public class NodeHelper {

    public static Node load(String nodeId) {
        //Load RootNode model
        RootNode rootNode = RootNode.findLatestPublishedVersionWithNodeId(nodeId, new Date());
        if (rootNode == null) {
            throw new PageNotFoundException(nodeId);
        }

        return load(rootNode);
    }

    public static Node load(String nodeId, long version) {
        //Load RootNode model
        RootNode rootNode = RootNode.findWithNodeIdAndSpecificVersion(nodeId, version);
        if (rootNode == null) {
            throw new PageNotFoundException(nodeId);
        }

        return load(rootNode);
    }

    public static Node load(RootNode rootNode) {
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

        return node;
    }

    /*
     * Convenience methods for hooks with NODE type
     */
    public static Node triggerProvidesNodeListener(String withType, RootNode rootNode) {
        return ProvidesHelper.triggerListener(Types.NODE, withType, rootNode);
    }

    public static void triggerBeforeNodeLoaded(String withType, RootNode rootNode) {
        OnLoadHelper.triggerBeforeListener(Types.NODE, withType, rootNode);
    }

    public static void triggerAfterNodeLoaded(String withType, Node node) {
        OnLoadHelper.triggerAfterListener(Types.NODE, withType, node);
    }

}

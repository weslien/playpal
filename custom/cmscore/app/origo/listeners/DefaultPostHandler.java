package origo.listeners;

import models.origo.core.RootNode;
import origo.helpers.NodeHelper;
import origo.helpers.OnPostHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.PostHandler;
import play.mvc.Scope;

public class DefaultPostHandler {

    @PostHandler
    public void handlePost(String nodeId, String formType, Scope.Params params) {
        RootNode rootNode = RootNode.findWithNodeIdAndLatestVersion(nodeId);
        if (rootNode == null) {
            throw new RuntimeException("Root node + " + nodeId + " does not exist");
        }

        Node node = NodeHelper.triggerProvidesNodeListener(rootNode.type, rootNode);

        OnPostHelper.triggerListener(formType, node, Scope.Params.class, params);
    }

}

package controllers.origo.core;

import models.origo.core.RootNode;
import origo.helpers.NodeHelper;
import origo.helpers.OnPostHelper;
import play.modules.origo.core.Node;
import play.mvc.Controller;
import play.mvc.Scope;

public class BasicPostController extends Controller {

    public static void submit() {

        String nodeId = params.get("_core_node_id");

        RootNode rootNode = RootNode.findWithNodeIdAndLatestVersion(nodeId);
        if (rootNode == null) {
            throw new RuntimeException("Root node + " + nodeId + " does not exist");
        }

        Node node = NodeHelper.triggerProvidesNodeListener(rootNode.type, rootNode);

        OnPostHelper.triggerListener(rootNode.type, node, Scope.Params.class, params);
    }

}

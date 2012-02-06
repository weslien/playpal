package controllers.origo.core;

import models.origo.core.RootNode;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

public class NodeController extends Controller {

    //@Get("/node")
    public static void nodeList() {

        //Load NodeModel
        List<RootNode> leaves = RootNode.findAllCurrentVersions(new Date());

        render(leaves);
    }

    //@Get("/node/{nodeId}")
    public static void node(@Required String nodeId) {

        //Load NodeModel
        RootNode node = RootNode.findLatestPublishedVersionWithNodeId(nodeId, new Date());

        render(node);
    }

    //@Get("/node/{nodeId}/all")
    public static void nodeVersions(@Required String nodeId) {

        List<RootNode> nodes = RootNode.findAllVersionsWithNodeId(nodeId);

        render(nodes);
    }

    //@Get("/node/{nodeId}/{<[0-9]+>version}")
    public static void nodeVersion(@Required String nodeId, @Required Long version) {

        RootNode node = RootNode.findWithNodeIdAndSpecificVersion(nodeId, version);

        render(node);
    }

}

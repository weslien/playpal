package controllers.cmscore;

import models.cmscore.RootNode;
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

    //@Get("/node/{uuid}")
    public static void node(@Required String uuid) {

        //Load NodeModel
        RootNode node = RootNode.findWithUuidLatestPublishedVersion(uuid, new Date());

        render(node);
    }

    //@Get("/node/{uuid}/all")
    public static void nodeVersions(@Required String uuid) {

        List<RootNode> nodes = RootNode.findWithUuidAllVersions(uuid);

        render(nodes);
    }

    //@Get("/node/{uuid}/{<[0-9]+>version}")
    public static void nodeVersion(@Required String uuid, @Required Long version) {

        RootNode node = RootNode.findWithUuidSpecificVersion(uuid, version);

        render(node);
    }

}

package controllers.cmscore;

import models.cmscore.Leaf;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

public class LeafController extends Controller {

    //@Get("/leaf")
    public static void leafList() {

        //Load leafModel
        List<Leaf> leaves = Leaf.findAllCurrentVersions(new Date());

        render(leaves);
    }

    //@Get("/leaf/{uuid}")
    public static void leaf(@Required String uuid) {

        //Load leafModel
        Leaf leaf = Leaf.findWithUuidLatestPublishedVersion(uuid, new Date());

        render(leaf);
    }

    //@Get("/leaf/{uuid}/all")
    public static void leafVersions(@Required String uuid) {

        List<Leaf> leaves = Leaf.findWithUuidAllVersions(uuid);

        render(leaves);
    }

    //@Get("/leaf/{uuid}/{<[0-9]+>version}")
    public static void leafVersion(@Required String uuid, @Required Long version) {

        Leaf leaf = Leaf.findWithUuidSpecificVersion(uuid, version);

        render(leaf);
    }

}

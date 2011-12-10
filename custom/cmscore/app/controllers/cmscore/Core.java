package controllers.cmscore;

import models.cmscore.Leaf;
import play.data.validation.Required;
import play.modules.cmscore.helpers.LeafHelper;
import play.mvc.Controller;

import java.util.Date;
import java.util.List;

public class Core extends Controller {

    public static void leafList() {
        List<Leaf> leaves = Leaf.findAllCurrentVersions(new Date());

        render(leaves);
    }

    public static void leaf(@Required String uuid) {

        //Load leafModel
        Leaf leaf = Leaf.findWithUuidLatestPublishedVersion(uuid, new Date());

        LeafHelper.triggerBeforeLeafLoaded(leaf.type, leaf);

        LeafHelper.triggerProvidesListener(leaf.type, leaf);

        LeafHelper.triggerAfterLeafLoaded(leaf.type, leaf);

        render(leaf);
    }

    public static void leafVersions(@Required String uuid) {

        List<Leaf> leaves = Leaf.findWithUuidAllVersions(uuid);

        render(leaves);
    }

    public static void leafVersion(@Required String uuid, @Required Long version) {

        Leaf leaf = Leaf.findWithUuidSpecificVersion(uuid, version);

        render(leaf);
    }
}

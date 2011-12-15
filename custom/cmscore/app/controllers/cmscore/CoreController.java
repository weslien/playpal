package controllers.cmscore;

import helpers.LeafHelper;
import models.cmscore.Leaf;
import play.data.validation.Required;
import play.modules.cmscore.LeafType;
import play.mvc.Controller;

import java.util.Date;

public class CoreController extends Controller {

    //@Get("/core/{uuid}")
    public static void index(@Required String uuid) {
        LeafType leaf = load(uuid);
        render(leaf);
    }

    private static LeafType load(String uuid) {
        //Load leafModel
        Leaf rootLeaf = Leaf.findWithUuidLatestPublishedVersion(uuid, new Date());

        boolean hasType = rootLeaf.type != null && rootLeaf.type != Leaf.class;
        if (hasType) {
            LeafHelper.triggerBeforeLeafLoaded(rootLeaf.type, rootLeaf);
        }

        LeafType leaf = rootLeaf;
        if (hasType) {
            leaf = LeafHelper.triggerProvidesListener(rootLeaf.type, rootLeaf);
        }

        if (hasType) {
            LeafHelper.triggerAfterLeafLoaded(rootLeaf.type, leaf);
        }

        return leaf;
    }

}

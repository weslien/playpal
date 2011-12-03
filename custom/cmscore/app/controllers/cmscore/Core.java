package controllers.cmscore;

import models.cmscore.Leaf;
import play.mvc.Controller;

import java.util.List;

public class Core extends Controller {

    public static void leafList() {
        List<Leaf> leafs = Leaf.findAll();

        render(leafs);
    }
    
    public static void leaf(String uuid){

        List<Leaf> leafs = Leaf.findWithUid(uuid);

        render(leafs);
    }

    public static void leafVersion(String uid, Integer version){

        Leaf leaf = Leaf.findWithUuidAndVersion(uid, version);

        render(leaf);
    }

}

package controllers;

import controllers.cmscore.CoreController;
import play.modules.cmscore.ui.RenderedLeaf;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        //TODO: Check for if config !exists and redirect to wizard

        RenderedLeaf leaf = CoreController.getStartPage();
        renderArgs.put("leaf", leaf);
        render(leaf.getTemplate(), leaf);
    }
    
    public static void page(String uuid) {
        //TODO: Check for if config !exists and redirect to wizard

        RenderedLeaf leaf = CoreController.getPage(uuid);
        renderArgs.put("leaf", leaf);
        render(leaf.getTemplate(), leaf);
    }

}
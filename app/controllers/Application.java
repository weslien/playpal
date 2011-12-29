package controllers;

import controllers.cmscore.CoreLoader;
import play.modules.cmscore.ui.RenderedLeaf;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        //TODO: Check for if config !exists and redirect to wizard

        RenderedLeaf leaf = CoreLoader.getStartPage();
        render(leaf.getTemplate(), leaf);
    }
    
    public static void page(String identifier) {
        //TODO: Check for if config !exists and redirect to wizard

        RenderedLeaf leaf = CoreLoader.getPage(identifier);
        render(leaf.getTemplate(), leaf);
    }

}
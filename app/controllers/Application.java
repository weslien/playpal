package controllers;

import controllers.cmscore.CoreLoader;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.RenderedLeaf;
import play.mvc.Controller;

import java.util.Collection;

public class Application extends Controller {

    public static void index() {
        //TODO: Check if config !exists and redirect to wizard

        RenderedLeaf leaf = CoreLoader.getStartPage();
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(leaf.getId());
        render(leaf.getTemplate(), leaf, navigation);
    }

    public static void page(String identifier) {
        //TODO: Check if config !exists and redirect to wizard

        RenderedLeaf leaf = CoreLoader.getPage(identifier);
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(identifier);
        render(leaf.getTemplate(), leaf, navigation);
    }

    public static void pageVersion(String identifier, long version) {
        //TODO: Check if config !exists and redirect to wizard

        RenderedLeaf leaf = CoreLoader.getPage(identifier, version);
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(identifier, version);
        render(leaf.getTemplate(), leaf, navigation);
    }

}
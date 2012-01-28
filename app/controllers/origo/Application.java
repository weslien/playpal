package controllers.origo;

import controllers.origo.core.CoreLoader;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;
import play.mvc.Controller;

import java.util.Collection;

public class Application extends Controller {

    public static void index() {
        //TODO: Check if config !exists and redirect to wizard

        RenderedNode node = CoreLoader.getStartPage();
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(node.getId());
        render(node.getTemplate(), node, navigation);
    }

    public static void page(String identifier) {
        //TODO: Check if config !exists and redirect to wizard

        RenderedNode node = CoreLoader.getPage(identifier);
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(identifier);
        render(node.getTemplate(), node, navigation);
    }

    public static void pageVersion(String identifier, long version) {
        //TODO: Check if config !exists and redirect to wizard

        RenderedNode node = CoreLoader.getPage(identifier, version);
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(identifier, version);
        render(node.getTemplate(), node, navigation);
    }

}
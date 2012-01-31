package controllers.origo.admin;

import controllers.origo.core.CoreLoader;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;
import play.mvc.Controller;

import java.util.Collection;

public class Application extends Controller {

    public static void index() {
        //TODO: Check if config !exists and redirect to wizard

        RenderedNode node = AdminLoader.getStartPage();
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(node.getId());
        render(node.getTemplate(), node, navigation);
    }

    public static void page(String identifier, String type) {
        //TODO: Check if config !exists and redirect to wizard

        RenderedNode node = AdminLoader.getPage(identifier, type);
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(identifier);
        render(node.getTemplate(), node, navigation);
    }

}

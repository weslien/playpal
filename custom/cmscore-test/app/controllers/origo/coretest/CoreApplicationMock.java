package controllers.origo.coretest;

import controllers.origo.core.CoreLoader;
import play.mvc.Controller;

import java.util.Collection;

public class CoreApplicationMock extends Controller {

    public static void index() {
        //TODO: Check if config !exists and redirect to wizard

        play.modules.origo.core.ui.RenderedNode node = CoreLoader.getStartPage();
        Collection<play.modules.origo.core.ui.NavigationElement> navigation = CoreLoader.getNavigation(node.getId());
        render(node.getTemplate(), node, navigation);
    }

    public static void page(String identifier) {
        //TODO: Check if config !exists and redirect to wizard

        play.modules.origo.core.ui.RenderedNode node = CoreLoader.getPage(identifier);
        Collection<play.modules.origo.core.ui.NavigationElement> navigation = CoreLoader.getNavigation(identifier);
        render(node.getTemplate(), node, navigation);
    }

    public static void pageVersion(String identifier, long version) {
        //TODO: Check if config !exists and redirect to wizard

        play.modules.origo.core.ui.RenderedNode node = CoreLoader.getPage(identifier, version);
        Collection<play.modules.origo.core.ui.NavigationElement> navigation = CoreLoader.getNavigation(identifier, version);
        render(node.getTemplate(), node, navigation);
    }

}

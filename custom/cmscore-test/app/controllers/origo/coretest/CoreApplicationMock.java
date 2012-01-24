package controllers.origo.coretest;

import controllers.origo.core.CoreLoader;
import play.mvc.Controller;

import java.util.Collection;

public class CoreApplicationMock extends Controller {

    public static void index() {
        play.modules.origo.core.ui.RenderedNode node = CoreLoader.getStartPage();
        Collection<play.modules.origo.core.ui.NavigationElement> navigation = CoreLoader.getNavigation(node.getId());
        render(node.getTemplate(), node, navigation);
    }

    public static void page(String identifier) {
        play.modules.origo.core.ui.RenderedNode node = CoreLoader.getPage(identifier);
        Collection<play.modules.origo.core.ui.NavigationElement> navigation = CoreLoader.getNavigation(identifier);
        render(node.getTemplate(), node, navigation);
    }

    public static void pageVersion(String identifier, long version) {
        play.modules.origo.core.ui.RenderedNode node = CoreLoader.getPage(identifier, version);
        Collection<play.modules.origo.core.ui.NavigationElement> navigation = CoreLoader.getNavigation(identifier, version);
        render(node.getTemplate(), node, navigation);
    }
}

package controllers.origo.structuredcontenttest;

import controllers.origo.core.CoreLoader;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;
import play.mvc.Controller;

import java.util.Collection;

public class StructuredContentMock extends Controller {

    public static void index() {
        RenderedNode node = CoreLoader.getStartPage();
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(node.getId());
        render(node.getTemplate(), node, navigation);
    }

    public static void page(String identifier) {
        RenderedNode node = CoreLoader.getPage(identifier);
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(identifier);
        render(node.getTemplate(), node, navigation);
    }

    public static void pageVersion(String identifier, long version) {
        RenderedNode node = CoreLoader.getPage(identifier, version);
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(identifier, version);
        render(node.getTemplate(), node, navigation);
    }
}
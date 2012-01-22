package controllers.cmscore;

import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.RenderedNode;
import play.mvc.Controller;

import java.util.Collection;

public class CmsCoreApplicationMock extends Controller {

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

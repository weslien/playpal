package controllers.origo.contactformtest;

import controllers.origo.core.CoreLoader;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;
import play.mvc.Controller;

import java.util.Collection;

public class ContactFormMock extends Controller {

    public static void index() {
        RenderedNode node = CoreLoader.getPage("c9615819-0556-4e70-b6a9-a66c5b8d4c1a");
        Collection<NavigationElement> navigation = CoreLoader.getNavigation(node.getId());
        render(node.getTemplate(), node, navigation);
    }

}
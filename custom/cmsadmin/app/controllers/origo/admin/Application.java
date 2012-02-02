package controllers.origo.admin;

import origo.listeners.PageNotFoundException;
import play.Logger;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;
import play.mvc.Controller;

import java.util.Collection;

public class Application extends Controller {

    public static void dashboard() {
        //TODO: Check if config !exists and redirect to wizard

        try {
            RenderedNode node = AdminLoader.getStartPage();
            Collection<NavigationElement> navigation = AdminLoader.getNavigation(node.getId());
            render(node.getTemplate(), node, navigation);
        } catch (PageNotFoundException e) {
            Logger.error("Page Not Found [dashboard]" + e.getLocalizedMessage(), e);
            notFound();
        } catch (Exception e) {
            Logger.error("Error: " + e.getMessage(), e);
            error(e);
        }
    }

    public static void pageWithType(String type) {
        //TODO: Check if config !exists and redirect to wizard

        try {
            RenderedNode node = AdminLoader.getPage(type);
            Collection<NavigationElement> navigation = AdminLoader.getNavigation(type);
            render(node.getTemplate(), node, navigation);
        } catch (PageNotFoundException e) {
            Logger.error("Page Not Found [" + type + "]" + e.getLocalizedMessage(), e);
            notFound();
        } catch (Exception e) {
            Logger.error("Error: " + e.getMessage(), e);
            error(e);
        }
    }

    public static void pageWithTypeAndIdentifier(String type, String identifier) {
        //TODO: Check if config !exists and redirect to wizard

        try {
            RenderedNode node = AdminLoader.getPage(type, identifier);
            Collection<NavigationElement> navigation = AdminLoader.getNavigation(type);
            render(node.getTemplate(), node, navigation);
        } catch (PageNotFoundException e) {
            Logger.error("Page Not Found [" + type + "] and identifier [" + identifier + "]" + e.getLocalizedMessage(), e);
            notFound();
        } catch (Exception e) {
            Logger.error("Error: " + e.getMessage(), e);
            error(e);
        }
    }

}

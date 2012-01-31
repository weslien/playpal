package controllers.origo.admin;

import models.origo.core.Alias;
import models.origo.core.RootNode;
import origo.helpers.NavigationHelper;
import origo.helpers.NodeHelper;
import origo.helpers.SettingsHelper;
import origo.helpers.ThemeHelper;
import origo.listeners.PageNotFoundException;
import play.Logger;
import play.modules.origo.admin.Admins;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;
import play.mvc.results.Redirect;

import java.util.Collection;

public class AdminLoader {

    // TODO: Make this into a setting instead of a static String
    private static final String DASHBOARD_PAGE_TYPE = "origo.admin.dashboard";

    public static RenderedNode getStartPage() {
        try {
            return loadAndDecorateStartPage();
        } catch (PageNotFoundException e) {
            throw redirectToPageNotFoundPage();
        } catch (Exception e) {
            Logger.error("An exception occurred while loading the start page: " + e.getMessage());
            throw redirectToInternalServerErrorPage();
        }
    }

    public static RenderedNode getPage(String identifier, String withType) {
        try {
            return loadAndDecoratePage(identifier, withType);
        } catch (PageNotFoundException e) {
            throw redirectToPageNotFoundPage();
        } catch (Exception e) {
            Logger.error("An exception occurred while loading the page [" + identifier + "]: " + e.getMessage());
            throw redirectToInternalServerErrorPage();
        }
    }

    private static RenderedNode loadAndDecorateStartPage() {
        String startPage = SettingsHelper.Admin.getStartPage();
        Logger.debug("Loading Start Page [" + startPage + "]");
        return loadAndDecoratePage(startPage, DASHBOARD_PAGE_TYPE);
    }

    public static Redirect redirectToPageNotFoundPage() {
        Logger.debug("Redirecting to Page-Not-Found Page");
        String pageNotFoundPage = SettingsHelper.Admin.getPageNotFoundPage();
        Collection<Alias> aliases = Alias.findWithPageId(pageNotFoundPage);
        if (aliases.iterator().hasNext()) {
            Alias alias = aliases.iterator().next();
            return new Redirect(SettingsHelper.Admin.getBaseUrl() + "" + alias.path, false);
        } else {
            // Defaulting to /page-not-found
            return new Redirect(SettingsHelper.Admin.getBaseUrl() + "page-not-found", false);
        }
    }

    public static Redirect redirectToInternalServerErrorPage() {
        Logger.debug("Redirecting to Page-Not-Found Page");
        String internalServerErrorPage = SettingsHelper.Admin.getInternalServerErrorPage();
        Collection<Alias> aliases = Alias.findWithPageId(internalServerErrorPage);
        if (aliases.iterator().hasNext()) {
            Alias alias = aliases.iterator().next();
            return new Redirect(SettingsHelper.Admin.getBaseUrl() + "" + alias.path, false);
        } else {
            // Defaulting to /error
            return new Redirect(SettingsHelper.Admin.getBaseUrl() + "error", false);
        }
    }

    private static RenderedNode loadAndDecoratePage(String identifier, String withType) {
        Node node = loadNode(identifier, withType);
        return decorateNode(node);
    }

    private static Node loadNode(String identifier, String withType) {
        Logger.trace("Trying to find alias for [" + identifier + "]");
        String page = Admins.aliases.get(identifier);
        if (page != null) {
            Logger.debug("Found alias: " + page);
            return loadByTypeIdentifier(page, withType);
        } else {
            Logger.debug("No Alias found trying [" + identifier + "] as nodeId");
            return loadByTypeIdentifier(page, withType);
        }
    }

    private static Node loadByTypeIdentifier(String identifier, final String withType) {

        RootNode rootNode = new RootNode(0L);
        rootNode.type = withType;
        rootNode.nodeId = identifier;

        return NodeHelper.load(rootNode);

    }

    private static RenderedNode decorateNode(Node node) {
        RenderedNode renderedNode = ThemeHelper.decorate(node);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Decorated " + renderedNode);
        }
        return renderedNode;
    }

    public static Collection<NavigationElement> getNavigation(String identifier) {
        return getNavigation(identifier, SettingsHelper.Admin.getNavigationType());
    }

    public static Collection<NavigationElement> getNavigation(String identifier, String withType) {
        Node node = loadNode(identifier, withType);
        Collection<NavigationElement> navigationLinks = NavigationHelper.getNavigation(node, NavigationElement.ADMIN);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Navigation loaded " + navigationLinks);
        }
        return navigationLinks;
    }


}

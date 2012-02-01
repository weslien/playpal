package controllers.origo.admin;

import models.origo.core.RootNode;
import origo.helpers.NavigationHelper;
import origo.helpers.NodeHelper;
import origo.helpers.SettingsHelper;
import origo.helpers.ThemeHelper;
import play.Logger;
import play.modules.origo.admin.Admins;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;

import java.util.Collection;

public class AdminLoader {

    public static RenderedNode getStartPage() {
        return loadAndDecorateStartPage();
    }

    public static RenderedNode getPage(String identifier, String withType) {
        return loadAndDecoratePage(identifier, withType);
    }

    private static RenderedNode loadAndDecorateStartPage() {
        String startPage = SettingsHelper.Admin.getStartPage();
        Logger.debug("Loading Start Page [" + startPage + "]");
        return loadAndDecoratePage(startPage, SettingsHelper.Admin.getDashboardType());
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
            return loadByTypeIdentifier(identifier, withType);
        }
    }

    private static Node loadByTypeIdentifier(String identifier, final String withType) {
        RootNode rootNode = loadRootNode(identifier, withType);
        return NodeHelper.load(rootNode);
    }

    private static RootNode loadRootNode(String identifier, String withType) {
        RootNode rootNode = new RootNode(0L);
        rootNode.type = withType;
        rootNode.nodeId = identifier;
        rootNode.themeVariant = SettingsHelper.Admin.getThemeVariant();
        return rootNode;
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
        Node node = loadRootNode(identifier, withType);
        Collection<NavigationElement> navigationLinks = NavigationHelper.getNavigation(node, NavigationElement.ADMIN);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Navigation loaded " + navigationLinks);
        }
        return navigationLinks;
    }


}

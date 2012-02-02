package controllers.origo.admin;

import models.origo.core.RootNode;
import origo.helpers.NavigationHelper;
import origo.helpers.NodeHelper;
import origo.helpers.SettingsHelper;
import origo.helpers.ThemeHelper;
import play.Logger;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;

import java.util.Collection;

public class AdminLoader {

    public static RenderedNode getStartPage() {
        return loadAndDecorateStartPage();
    }

    public static RenderedNode getPage(String withType) {
        return loadAndDecoratePage(withType);
    }

    private static RenderedNode loadAndDecorateStartPage() {
        return loadAndDecoratePage(SettingsHelper.Admin.getDashboardType());
    }

    private static RenderedNode loadAndDecoratePage(String withType) {
        Node node = loadNode(withType);
        return decorateNode(node);
    }

    private static Node loadNode(String withType) {
        Logger.debug("Loading [" + withType + "] as type");
        return loadByType(withType);
    }

    private static Node loadByType(final String withType) {
        RootNode rootNode = loadRootNode(withType);
        return NodeHelper.load(rootNode);
    }

    private static RootNode loadRootNode(String withType) {
        RootNode rootNode = new RootNode(0L);
        rootNode.type = withType;
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

    public static Collection<NavigationElement> getNavigation(String withType) {
        Node node = loadRootNode(withType);
        Collection<NavigationElement> navigationLinks = NavigationHelper.getNavigation(node, NavigationElement.ADMIN);
        if (Logger.isDebugEnabled()) {
            Logger.debug("Navigation loaded " + navigationLinks);
        }
        return navigationLinks;
    }


}

package controllers.cmscore;

import helpers.NavigationHelper;
import helpers.NodeHelper;
import helpers.SettingsHelper;
import helpers.ThemeHelper;
import listeners.PageNotFoundException;
import models.cmscore.Alias;
import org.apache.log4j.Logger;
import play.modules.cmscore.Node;
import play.modules.cmscore.ui.NavigationElement;
import play.modules.cmscore.ui.RenderedNode;

import java.util.Collection;

public class CoreLoader {

    private final static Logger LOG = Logger.getLogger(CoreLoader.class);

    public static RenderedNode getStartPage() {
        try {
            return loadAndDecorateStartPage();
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    public static RenderedNode getPage(String uuid) {
        try {
            return CoreLoader.loadAndDecoratePage(uuid, 0);
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    public static RenderedNode getPage(String uuid, long version) {
        try {
            return CoreLoader.loadAndDecoratePage(uuid, version);
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    private static RenderedNode loadAndDecorateStartPage() {
        String startPage = SettingsHelper.getStartPage();
        LOG.debug("Loading Start Page [" + startPage + "]");
        return CoreLoader.loadAndDecoratePage(startPage, 0);
    }

    // TODO: This should be a redirect to the /page-not-found page so that it is not cached incorrectly downstream
    // TODO: this method should also be readily accessible by third party modules to all 404 management is streamlined
    private static RenderedNode loadAndDecoratePageNotFoundPage() {
        String pageNotFoundPage = SettingsHelper.getPageNotFoundPage();
        LOG.debug("Loading Start Page [" + pageNotFoundPage + "]");
        return CoreLoader.loadAndDecoratePage(pageNotFoundPage, 0);
    }

    private static RenderedNode loadAndDecoratePage(String identifier, long version) {
        Node node = loadNode(identifier, version);
        return decorateNode(node);
    }

    private static Node loadNode(String identifier, long version) {
        LOG.debug("Trying to find alias for [" + identifier + "]");
        Alias alias = Alias.findWithPath(identifier);
        if (alias != null) {
            LOG.trace("Found alias: " + alias.toString());
            return loadByUUIDAndVersion(alias.pageId, version);
        } else {
            LOG.trace("Trying to find page with uuid [" + identifier + "]");
            return loadByUUIDAndVersion(identifier, version);
        }
    }

    private static Node loadByUUIDAndVersion(String identifier, long version) {
        Node node;
        if (version != 0) {
            node = NodeHelper.load(identifier, version);
        } else {
            node = NodeHelper.load(identifier);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded " + node.toString());
        }
        return node;
    }

    private static RenderedNode decorateNode(Node node) {
        RenderedNode renderedNode = ThemeHelper.decorate(node);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Decorated " + renderedNode);
        }
        return renderedNode;
    }

    public static Collection<NavigationElement> getNavigation(String identifier) {
        return getNavigation(identifier, 0);
    }

    public static Collection<NavigationElement> getNavigation(String identifier, long version) {
        Node node = loadNode(identifier, version);
        Collection<NavigationElement> navigationLinks = NavigationHelper.getNavigation(node, NavigationElement.FRONT);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Navigation loaded " + navigationLinks);
        }
        return navigationLinks;
    }

}

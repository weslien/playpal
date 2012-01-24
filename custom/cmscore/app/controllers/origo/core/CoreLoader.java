package controllers.origo.core;

import models.origo.core.Alias;
import org.apache.log4j.Logger;
import origo.helpers.NavigationHelper;
import origo.helpers.NodeHelper;
import origo.helpers.SettingsHelper;
import origo.helpers.ThemeHelper;
import origo.listeners.PageNotFoundException;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.NavigationElement;
import play.modules.origo.core.ui.RenderedNode;
import play.mvc.results.Redirect;

import java.util.Collection;

public class CoreLoader {

    private final static Logger LOG = Logger.getLogger(CoreLoader.class);

    public static RenderedNode getStartPage() {
        try {
            return loadAndDecorateStartPage();
        } catch (PageNotFoundException e) {
            throw redirectToPageNotFoundPage();
        } catch (Exception e) {
            LOG.error("An exception occured while loading the start page", e);
            throw redirectToInternalServerErrorPage();
        }
    }

    public static RenderedNode getPage(String identifier) {
        try {
            return CoreLoader.loadAndDecoratePage(identifier, 0);
        } catch (PageNotFoundException e) {
            throw redirectToPageNotFoundPage();
        } catch (Exception e) {
            LOG.error("An exception occured while loading the page [" + identifier + "]", e);
            throw redirectToInternalServerErrorPage();
        }
    }

    public static RenderedNode getPage(String identifier, long version) {
        try {
            return CoreLoader.loadAndDecoratePage(identifier, version);
        } catch (PageNotFoundException e) {
            throw redirectToPageNotFoundPage();
        } catch (Exception e) {
            LOG.error("An exception occured while loading the page [" + identifier + "] with specific version [" + version + "]", e);
            throw redirectToInternalServerErrorPage();
        }
    }

    private static RenderedNode loadAndDecorateStartPage() {
        String startPage = SettingsHelper.getStartPage();
        LOG.debug("Loading Start Page [" + startPage + "]");
        return CoreLoader.loadAndDecoratePage(startPage, 0);
    }

    public static Redirect redirectToPageNotFoundPage() {
        LOG.debug("Redirecting to Page-Not-Found Page");
        String pageNotFoundPage = SettingsHelper.getPageNotFoundPage();
        Collection<Alias> aliases = Alias.findWithPageId(pageNotFoundPage);
        if (aliases.iterator().hasNext()) {
            Alias alias = aliases.iterator().next();
            return new Redirect(SettingsHelper.getBaseUrl() + "" + alias.path, false);
        } else {
            // Defaulting to /page-not-found
            return new Redirect(SettingsHelper.getBaseUrl() + "page-not-found", false);
        }
    }

    public static Redirect redirectToInternalServerErrorPage() {
        LOG.debug("Redirecting to Page-Not-Found Page");
        String internalServerErrorPage = SettingsHelper.getInternalServerErrorPage();
        Collection<Alias> aliases = Alias.findWithPageId(internalServerErrorPage);
        if (aliases.iterator().hasNext()) {
            Alias alias = aliases.iterator().next();
            return new Redirect(SettingsHelper.getBaseUrl() + "" + alias.path, false);
        } else {
            // Defaulting to /error
            return new Redirect(SettingsHelper.getBaseUrl() + "error", false);
        }
    }

    private static RenderedNode loadAndDecoratePage(String identifier, long version) {
        play.modules.origo.core.Node node = loadNode(identifier, version);
        return decorateNode(node);
    }

    private static play.modules.origo.core.Node loadNode(String identifier, long version) {
        LOG.trace("Trying to find alias for [" + identifier + "]");
        Alias alias = Alias.findWithPath(identifier);
        if (alias != null) {
            LOG.debug("Found alias: " + alias.toString());
            return loadByUUIDAndVersion(alias.pageId, version);
        } else {
            LOG.debug("No Alias found trying [" + identifier + "] as uuid");
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

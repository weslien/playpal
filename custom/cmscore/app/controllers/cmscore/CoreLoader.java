package controllers.cmscore;

import helpers.LeafHelper;
import helpers.SettingsHelper;
import helpers.ThemeHelper;
import listeners.PageNotFoundException;
import models.cmscore.Alias;
import org.apache.log4j.Logger;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.RenderedLeaf;

public class CoreLoader {

    private final static Logger LOG = Logger.getLogger(CoreLoader.class);

    public static RenderedLeaf getStartPage() {
        try {
            return loadAndDecorateStartPage();
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    public static RenderedLeaf getPage(String uuid) {
        try {
            return CoreLoader.loadAndDecoratePage(uuid, 0);
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    public static RenderedLeaf getPage(String uuid, long version) {
        try {
            return CoreLoader.loadAndDecoratePage(uuid, version);
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    private static RenderedLeaf loadAndDecorateStartPage() {
        String startPage = SettingsHelper.getStartPage();
        LOG.debug("Loading Start Page [" + startPage + "]");
        return CoreLoader.loadAndDecoratePage(startPage, 0);
    }

    // TODO: This should be a redirect to the /page-not-found page so that it is not cached incorrectly downstream
    // TODO: this method should also be readily accessible by third party modules to all 404 management is streamlined
    private static RenderedLeaf loadAndDecoratePageNotFoundPage() {
        String pageNotFoundPage = SettingsHelper.getPageNotFoundPage();
        LOG.debug("Loading Start Page [" + pageNotFoundPage + "]");
        return CoreLoader.loadAndDecoratePage(pageNotFoundPage, 0);
    }

    private static RenderedLeaf loadAndDecoratePage(String identifier, long version) {
        LOG.debug("Trying to find alias for [" + identifier + "]");
        Alias alias = Alias.findWithPath(identifier);
        if (alias != null) {
            LOG.trace("Found alias: " + alias.toString());
            Leaf leaf = loadByUUIDAndVersion(alias.pageId, version);
            return decorateLeaf(leaf);
        } else {
            LOG.trace("Trying to find page with uuid [" + identifier + "]");
            Leaf leaf = loadByUUIDAndVersion(identifier, version);
            return decorateLeaf(leaf);
        }
    }

    private static Leaf loadByUUIDAndVersion(String identifier, long version) {
        Leaf leaf;
        if (version != 0) {
            leaf = LeafHelper.load(identifier, version);
        } else {
            leaf = LeafHelper.load(identifier);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded " + leaf.toString());
        }
        return leaf;
    }

    private static RenderedLeaf decorateLeaf(Leaf leaf) {
        RenderedLeaf renderedLeaf = ThemeHelper.decorate(leaf);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Decorated " + renderedLeaf);
        }
        return renderedLeaf;
    }

}

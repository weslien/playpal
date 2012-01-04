package controllers.cmscore;

import helpers.LeafHelper;
import helpers.ThemeHelper;
import listeners.PageNotFoundException;
import models.cmscore.Alias;
import models.cmscore.Settings;
import models.cmscore.SettingsKeys;
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
            return CoreLoader.loadAndDecoratePage(uuid);
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    private static RenderedLeaf loadAndDecorateStartPage() {
        Settings settings = Settings.load();
        String startPage = settings.getValue(SettingsKeys.Core.START_PAGE);
        LOG.debug("Loading Start Page ["+startPage+"]");
        return CoreLoader.loadAndDecoratePage(startPage);
    }

    // TODO: This should be a redirect to the /page-not-found page so that it is not cached incorrectly downstream
    // TODO: this method should also be readily accessible by third party modules to all 404 management is streamlined
    private static RenderedLeaf loadAndDecoratePageNotFoundPage() {
        Settings settings = Settings.load();
        String pageNotFoundPage = settings.getValue(SettingsKeys.Core.PAGE_NOT_FOUND_PAGE);
        LOG.debug("Loading Start Page ["+pageNotFoundPage+"]");
        return CoreLoader.loadAndDecoratePage(pageNotFoundPage);
    }

    private static RenderedLeaf loadAndDecoratePage(String identifier) {
        LOG.debug("Trying to find alias for [" + identifier + "]");
        Alias alias = Alias.findWithPath(identifier);
        if (alias != null) {
            LOG.trace("Found alias: "+ alias.toString());
            Leaf leaf = loadByUUID(alias.uuid);
            return decorateLeaf(leaf);
        } else {
            LOG.trace("Trying to find page with uuid ["+identifier+"]");
            Leaf leaf = loadByUUID(identifier);
            return decorateLeaf(leaf);
        }
    }

    private static Leaf loadByUUID(String identifier) {
        Leaf leaf = LeafHelper.load(identifier);
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

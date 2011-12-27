package controllers.cmscore;

import helpers.LeafHelper;
import helpers.ThemeHelper;
import listeners.PageNotFoundException;
import models.cmscore.Settings;
import models.cmscore.SettingsKeys;
import org.apache.log4j.Logger;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.ui.RenderedLeaf;
import play.mvc.Controller;

public class CoreController extends Controller {

    private final static Logger LOG = Logger.getLogger(CoreController.class); 

    public static RenderedLeaf getStartPage() {
        try {
            return loadAndDecorateStartPage();
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    public static RenderedLeaf getPage(String uuid) {
        try {
            return CoreController.loadAndDecoratePage(uuid);
        } catch (PageNotFoundException e) {
            return loadAndDecoratePageNotFoundPage();
        }
    }

    private static RenderedLeaf loadAndDecorateStartPage() {
        Settings settings = Settings.load();
        String startPage = settings.getValue(SettingsKeys.Core.START_PAGE);
        LOG.debug("Loading Start Page ["+startPage+"]");
        return CoreController.loadAndDecoratePage(startPage);
    }

    private static RenderedLeaf loadAndDecoratePageNotFoundPage() {
        RenderedLeaf leaf;
        String pageNotFoundPage = Settings.load().getValue(SettingsKeys.Core.PAGE_NOT_FOUND_PAGE);
        leaf = CoreController.loadAndDecoratePage(pageNotFoundPage);
        return leaf;
    }

    private static RenderedLeaf loadAndDecoratePage(String uuid) {
        LOG.debug("Loading Page ["+uuid+"]");
        Leaf leaf = LeafHelper.load(uuid);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded " + leaf.toString());
        }
        RenderedLeaf renderedLeaf = ThemeHelper.decorate(leaf);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Decorated " + renderedLeaf);
        }
        return renderedLeaf;
    }

}

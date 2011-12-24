package controllers.cmscore;

import helpers.LeafHelper;
import helpers.ThemeHelper;
import org.apache.log4j.Logger;
import play.modules.cmscore.Leaf;
import play.mvc.Controller;

public class CoreController extends Controller {

    private final static Logger LOG = Logger.getLogger(CoreController.class); 
    
    public static play.modules.cmscore.ui.RenderedLeaf getPage(String uuid) {
        Leaf leaf = LeafHelper.load(uuid);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loaded " + leaf.toString());
        }
        play.modules.cmscore.ui.RenderedLeaf renderedLeaf = ThemeHelper.decorate(leaf);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Decorated " + renderedLeaf);
        }
        return renderedLeaf;
    }


    

}

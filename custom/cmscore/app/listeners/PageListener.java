package listeners;

import models.cmscore.Leaf;
import models.cmscore.Page;
import play.modules.cmscore.annotations.Provides;

public class PageListener {

    @Provides(type = Page.class)
    public Page createPage(Leaf rootLeaf) {

        Page page = Page.findWithUuidSpecificVersion(rootLeaf.uuid, rootLeaf.version);
        page.leaf = rootLeaf;
        return page;
    }

}

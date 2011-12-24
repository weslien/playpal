package listeners;

import models.cmscore.Page;
import models.cmscore.RootLeaf;
import play.modules.cmscore.annotations.Provides;

public class PageListener {

    @Provides(type = Page.class)
    public static Page createPage(RootLeaf rootRootLeaf) {

        Page page = Page.findWithUuidSpecificVersion(rootRootLeaf.uuid, rootRootLeaf.version);
        page.rootLeaf = rootRootLeaf;
        return page;
    }

}

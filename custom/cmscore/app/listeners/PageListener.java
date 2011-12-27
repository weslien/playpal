package listeners;

import models.cmscore.Page;
import models.cmscore.RootLeaf;
import play.modules.cmscore.annotations.Provides;

public class PageListener {

    @Provides(type = Page.class)
    public static Page createPage(RootLeaf rootLeaf) {

        Page page = Page.findWithUuidSpecificVersion(rootLeaf.uuid, rootLeaf.version);
        if (page == null) {
            throw new PageNotFoundException(rootLeaf.uuid);
        }
        page.rootLeaf = rootLeaf;

        return page;
    }

}

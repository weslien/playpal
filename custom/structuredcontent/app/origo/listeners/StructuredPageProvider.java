package origo.listeners;

import models.origo.core.RootNode;
import models.origo.structuredcontent.Segment;
import models.origo.structuredcontent.StructuredPage;
import origo.helpers.SegmentHelper;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;

import java.util.List;

public class StructuredPageProvider {

    @Provides(type = Provides.NODE, with = "models.origo.structuredcontent.StructuredPage")
    public static Node loadPage(RootNode rootNode) {
        StructuredPage page = StructuredPage.findWithUuidSpecificVersion(rootNode.uuid, rootNode.version);
        if (page == null) {
            throw new PageNotFoundException(rootNode.uuid);
        }
        page.rootNode = rootNode;

        return page;
    }

    @OnLoad(type = OnLoad.TYPE_NODE, with = "models.origo.structuredcontent.StructuredPage")
    public static void loadContent(Node node) {

        List<Segment> segmentModels = Segment.findWithUuidSpecificVersion(node.getNodeId(), node.getVersion());
        for (Segment segment : segmentModels) {
            SegmentHelper.triggerBeforeSegmentLoaded(segment.type, node, segment);
            UIElement element = SegmentHelper.triggerSegmentProvider(segment.type, node, segment);
            SegmentHelper.triggerAfterSegmentLoaded(segment.type, node, segment, element);
            node.addUIElement(element);
        }

    }

}

package origo.helpers;

import models.origo.structuredcontent.Segment;
import play.modules.origo.core.Node;
import play.modules.origo.core.ui.UIElement;

public class SegmentHelper {

    public static final String TYPE_SEGMENT = "segment";

    /*
     * Convenience methods for hooks with SEGMENT type
     */
    public static UIElement triggerSegmentProvider(String withType, Node node, Segment segment) {
        return ProvidesHelper.triggerListener(TYPE_SEGMENT, withType, node, Segment.class, segment);
    }

    public static void triggerBeforeSegmentLoaded(String nodeType, Node node, Segment segment) {
        OnLoadHelper.triggerBeforeListener(TYPE_SEGMENT, nodeType, node, Segment.class, segment);
    }

    public static void triggerAfterSegmentLoaded(String withType, Node node, Segment segment, UIElement uiElement) {
        OnLoadHelper.triggerAfterListener(TYPE_SEGMENT, withType, node, Segment.class, segment, uiElement);
    }

}

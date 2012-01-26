package origo.listeners;

import models.origo.core.Content;
import models.origo.structuredcontent.Segment;
import org.apache.commons.lang.StringUtils;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.annotations.UIElementType;
import play.modules.origo.core.ui.UIElement;

public class SegmentProvider {

    @Provides(type = "segment", with = "models.origo.core.Content")
    public static UIElement createSegment(Segment segment) {
        if (!StringUtils.isBlank(segment.referenceId)) {
            Content content = Content.findWithIdentifier(segment.referenceId);
            if (content != null) {
                return new UIElement(content.identifier, UIElementType.TEXT, content.value);
            }
        }
        //TODO: Handle this somehow, in dev/admin maybe show a UIElement with a warning message and in prod swallow error?
        return null;
    }

}

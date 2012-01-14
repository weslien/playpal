package play.modules.cmscore;

import play.modules.cmscore.annotations.UIElementType;

public interface Renderable {
    
    String getContent();
    UIElementType getType();
    
}

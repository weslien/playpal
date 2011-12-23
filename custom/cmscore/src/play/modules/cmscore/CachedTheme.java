package play.modules.cmscore;

import java.lang.reflect.Method;
import java.util.Collection;

public class CachedTheme {

    private String id;
    private Method templateMethod;
    private Collection<String> contentAreas;
    
    public CachedTheme(String id, Method templateMethod, Collection<String> contentAreas) {
        this.id = id;
        this.templateMethod = templateMethod;
        this.contentAreas = contentAreas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Method getTemplateMethod() {
        return templateMethod;
    }

    public void setTemplateMethod(Method templateMethod) {
        this.templateMethod = templateMethod;
    }

    public Collection<String> getContentAreas() {
        return contentAreas;
    }

    public void setContentAreas(Collection<String> contentAreas) {
        this.contentAreas = contentAreas;
    }
}

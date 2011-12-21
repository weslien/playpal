package play.modules.cmscore;

import play.modules.cmscore.annotations.Theme;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ThemeCachedAnnotation extends CachedAnnotation {

    private Theme theme;
    
    public ThemeCachedAnnotation(Theme theme, Annotation annotation, Method method) {
        super(annotation, method);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}

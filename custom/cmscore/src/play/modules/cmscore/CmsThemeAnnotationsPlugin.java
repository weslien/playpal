package play.modules.cmscore;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.modules.cmscore.annotations.Theme;
import play.modules.cmscore.annotations.ThemeVariant;
import play.utils.Java;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Scans updated classes for \@Theme and \@ThemeVariant annotations.
 * At startup it scans all the classes in the classpath.
 * @see Theme
 * @see ThemeVariant
 */
public class CmsThemeAnnotationsPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        onClassesChange(Play.classes.all());
    }

    @Override
    public List<ApplicationClasses.ApplicationClass> onClassesChange(List<ApplicationClasses.ApplicationClass> modifiedClasses) {
        List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(modifiedClasses);
        findAndCacheAnnotation(modifiedJavaClasses);
        return modifiedClasses;
    }

    private void findAndCacheAnnotation(List<Class> modifiedClasses) {
        for (Class cls : modifiedClasses) {
            if (cls.isAnnotationPresent(Theme.class)) {
                Themes.invalidate(cls);
                @SuppressWarnings("unchecked")
                Theme theme = (Theme)cls.getAnnotation(Theme.class);
                Themes.addTheme(theme.id(), cls);
                List<Method> annotatedMethods = Java.findAllAnnotatedMethods(cls, ThemeVariant.class);
                for (Method m : annotatedMethods) {
                    ThemeVariant themeVariant = m.getAnnotation(ThemeVariant.class);
                    // TODO: check that the return type of the method is a String (template)
                    // TODO: check that at least 1 content area is supplied
                    Themes.addThemeVariant(theme.id(), themeVariant.id(), m, themeVariant.regions());
                }
            }
        }
    }

}

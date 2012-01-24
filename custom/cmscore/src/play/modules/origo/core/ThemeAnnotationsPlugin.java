package play.modules.origo.core;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.utils.Java;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Scans updated classes for \@Theme and \@ThemeVariant annotations.
 * At startup it scans all the classes in the classpath.
 *
 * @see play.modules.origo.core.annotations.Theme
 * @see play.modules.origo.core.annotations.ThemeVariant
 */
public class ThemeAnnotationsPlugin extends PlayPlugin {

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
            if (cls.isAnnotationPresent(play.modules.origo.core.annotations.Theme.class)) {
                Themes.invalidate(cls);
                @SuppressWarnings("unchecked")
                play.modules.origo.core.annotations.Theme theme = (play.modules.origo.core.annotations.Theme) cls.getAnnotation(play.modules.origo.core.annotations.Theme.class);
                Themes.addTheme(theme.id(), cls);
                List<Method> annotatedMethods = Java.findAllAnnotatedMethods(cls, play.modules.origo.core.annotations.ThemeVariant.class);
                for (Method m : annotatedMethods) {
                    play.modules.origo.core.annotations.ThemeVariant themeVariant = m.getAnnotation(play.modules.origo.core.annotations.ThemeVariant.class);
                    // TODO: check that the return type of the method is a String (template)
                    // TODO: check that at least 1 content area is supplied
                    Themes.addThemeVariant(theme.id(), themeVariant.id(), m, themeVariant.regions());
                }
            }
        }
    }

}

package play.modules.cmscore;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.modules.cmscore.annotations.Decorate;
import play.modules.cmscore.annotations.Theme;
import play.utils.Java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class CmsThemeAnnotationPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        onClassesChange(Play.classes.all());
    }

    @Override
    public List<ApplicationClasses.ApplicationClass> onClassesChange(List<ApplicationClasses.ApplicationClass> modifiedClasses) {

        List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(modifiedClasses);
        for (Class cls : modifiedJavaClasses) {
            if (cls.isAnnotationPresent(Theme.class)) {
                //noinspection unchecked
                findAndCacheAnnotation((Theme)cls.getAnnotation(Theme.class), Decorate.class, modifiedJavaClasses);
            }
        }

        return super.onClassesChange(modifiedClasses);
    }

    private void findAndCacheAnnotation(Theme theme, Class<? extends Annotation> annotationClass, List<Class> modifiedClasses) {
        List<Method> provides = Java.findAllAnnotatedMethods(modifiedClasses, annotationClass);
        for (Method m : provides) {
            Themes.addDecorator(theme, m.getAnnotation(annotationClass), m);
        }
    }

}

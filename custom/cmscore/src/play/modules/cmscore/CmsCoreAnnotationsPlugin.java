package play.modules.cmscore;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.modules.cmscore.annotations.LeafLoaded;
import play.modules.cmscore.annotations.Provides;
import play.utils.Java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class CmsCoreAnnotationsPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        onClassesChange(Play.classes.all());
    }

    @Override
    public List<ApplicationClasses.ApplicationClass> onClassesChange(List<ApplicationClasses.ApplicationClass> modifiedClasses) {

        List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(modifiedClasses);
        findAndAddListenerAnnotation(Provides.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(LeafLoaded.class, modifiedJavaClasses);

        return super.onClassesChange(modifiedClasses);
    }

    private void findAndAddListenerAnnotation(Class<? extends Annotation> annotationClass, List<Class> modifiedClasses) {
        List<Method> provides = Java.findAllAnnotatedMethods(modifiedClasses, annotationClass);
        for (Method m : provides) {
            Listeners.addListener(m.getAnnotation(annotationClass), m);
        }
    }

}

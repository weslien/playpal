package play.modules.cmscore;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.utils.Java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PlaypalAnnotationsPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        onClassesChange(Play.classes.all());
    }

    @Override
    public List<ApplicationClasses.ApplicationClass> onClassesChange(List<ApplicationClasses.ApplicationClass> modifiedClasses) {

        List<Class> modifiedJavaClasses = getJavaClasses(modifiedClasses);
        findAndAddAnnotation(Provides.class, modifiedJavaClasses);
        findAndAddAnnotation(AfterLeafLoaded.class, modifiedJavaClasses);
        findAndAddAnnotation(BeforeLeafLoaded.class, modifiedJavaClasses);

        return super.onClassesChange(modifiedClasses);
    }

    private static void findAndAddAnnotation(Class<? extends Annotation> annotationClass, List<Class> modifiedClasses) {
        List<Method> provides = Java.findAllAnnotatedMethods(modifiedClasses, annotationClass);
        for (Method m : provides) {
            Listeners.addListener(m.getAnnotation(annotationClass), m);
        }
    }

    public List<Class> getJavaClasses(final List<ApplicationClasses.ApplicationClass> classes) {
        List<Class> returnValues = new ArrayList<Class>();
        for (ApplicationClasses.ApplicationClass cls : classes) {
            if (cls.javaClass != null && !cls.javaClass.isInterface() && !cls.javaClass.isAnnotation()) {
                Listeners.invalidate(cls.javaClass);
                returnValues.add(cls.javaClass);
            }
        }
        return returnValues;
    }

}

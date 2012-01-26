package play.modules.origo.core;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.utils.Java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Scans classes that are compiled for methods annotated with trigger/add-ons/listener annotations and caches them.
 * At startup it scans all classes in the classpath.
 */
public class CoreAnnotationsPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        onClassesChange(Play.classes.all());
    }

    @Override
    public List<ApplicationClasses.ApplicationClass> onClassesChange(List<ApplicationClasses.ApplicationClass> modifiedClasses) {

        List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(modifiedClasses);
        findAndAddListenerAnnotation(play.modules.origo.core.annotations.Provides.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(play.modules.origo.core.annotations.Decorates.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(play.modules.origo.core.annotations.OnLoad.class, modifiedJavaClasses);

        return super.onClassesChange(modifiedClasses);
    }

    /**
     * Finds all methods annotated with the specified annotationClass and adds it to the cache.
     *
     * @param annotationClass a method annotation that provides a hook/trigger/listener.
     * @param modifiedClasses all classes modified in this compile (or on startup).
     */
    // TODO: should take a method to be triggered for additional checks of validity (checking return type, checking parameter types, etc).
    private void findAndAddListenerAnnotation(Class<? extends Annotation> annotationClass, List<Class> modifiedClasses) {
        List<Method> annotatedMethods = Java.findAllAnnotatedMethods(modifiedClasses, annotationClass);
        for (Method m : annotatedMethods) {
            Listeners.addListener(m.getAnnotation(annotationClass), m);
        }
    }

}
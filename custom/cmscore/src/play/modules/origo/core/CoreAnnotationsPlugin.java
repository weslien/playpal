package play.modules.origo.core;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.modules.origo.core.annotations.Decorates;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.annotations.forms.*;
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

        //TODO: Should only have to look at modified classes but the invalidation isn't working so for now we reload all classes
        List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(Play.classes.all());
        Listeners.invalidate();

        //List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(modifiedClasses);
        findAndAddListenerAnnotation(Provides.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(Decorates.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(OnLoad.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(OnSubmit.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(SubmitHandler.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(SubmitState.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(OnLoadForm.class, modifiedJavaClasses);
        findAndAddListenerAnnotation(ProvidesForm.class, modifiedJavaClasses);

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

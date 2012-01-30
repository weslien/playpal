package play.modules.origo.admin;

import play.Play;
import play.PlayPlugin;
import play.classloading.ApplicationClasses;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.AnnotationPluginHelper;
import play.utils.Java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Scans updated classes for \@Admin.Page and \@Admin.Segment annotations.
 * At startup it scans all the classes in the classpath.
 *
 * @see play.modules.origo.admin.annotations.Admin.Page
 */
public class AdminAnnotationsPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        onClassesChange(Play.classes.all());
    }

    @Override
    public List<ApplicationClasses.ApplicationClass> onClassesChange(List<ApplicationClasses.ApplicationClass> modifiedClasses) {

        //TODO: Should only have to look at modified classes but the invalidation isn't working so for now we reload all classes
        List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(Play.classes.all());
        Admins.invalidate();

        //List<Class> modifiedJavaClasses = AnnotationPluginHelper.getJavaClasses(modifiedClasses);
        findAndAddAdminPages(modifiedJavaClasses);

        return super.onClassesChange(modifiedClasses);
    }

    /**
     * Finds all methods annotated with the specified annotationClass and adds it to the cache.
     *
     * @param modifiedClasses all classes modified in this compile (or on startup).
     */
    // TODO: should take a method to be triggered for additional checks of validity (checking return type, checking parameter types, etc).
    private void findAndAddAdminPages(List<Class> modifiedClasses) {
        List<Method> annotatedMethods = Java.findAllAnnotatedMethods(modifiedClasses, Admin.Page.class);
        for (Method m : annotatedMethods) {
            Admin.Page page = m.getAnnotation(Admin.Page.class);
            Admins.addPage(page, m);
        }
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
            Admins.addListener(m.getAnnotation(annotationClass), m);
        }
    }

}

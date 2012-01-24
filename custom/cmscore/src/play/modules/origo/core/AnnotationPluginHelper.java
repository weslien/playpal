package play.modules.origo.core;

import play.classloading.ApplicationClasses;

import java.util.ArrayList;
import java.util.List;

public class AnnotationPluginHelper {

    public static List<Class> getJavaClasses(final List<ApplicationClasses.ApplicationClass> classes) {
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

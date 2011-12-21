package play.modules.cmscore;

import play.classloading.ApplicationClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    public static void invalidate(Class cls, Collection<List<CachedAnnotation>> listeners) {
        for (List<CachedAnnotation> annotationTypes : listeners) {
            for (Iterator<CachedAnnotation> listenerIterator = annotationTypes.iterator(); listenerIterator.hasNext(); ) {
                CachedAnnotation listener = listenerIterator.next();
                if (listener.method.getDeclaringClass().equals(cls)) {
                    listenerIterator.remove();
                }
            }
        }
    }

}

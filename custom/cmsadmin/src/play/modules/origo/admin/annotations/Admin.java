package play.modules.origo.admin.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Admin {

    public static final String DASHBOARD = "dashboard_item";
    public static final String RICHTEXT_EDITOR = "richtext_editor";

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public static @interface Navigation {
        String name();

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public static @interface NavigationItem {
        String name();

        String parent();
    }

}

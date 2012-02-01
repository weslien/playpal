package origo.themes;

import play.modules.origo.core.annotations.Theme;
import play.modules.origo.core.annotations.ThemeVariant;

@Theme(id = "admin")
public class AdminTheme {

    public static final String DEFAULT_VARIANT_NAME = "admin-default";

    @ThemeVariant(id = DEFAULT_VARIANT_NAME, regions = "main")
    public static String getDefaultTemplate() {
        return "themes/origo/AdminTheme/default.html";
    }

}

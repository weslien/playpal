package controllers.cmscore.themes;

import play.modules.cmscore.annotations.Theme;
import play.modules.cmscore.annotations.ThemeVariant;

@Theme(id = "default")
public class DefaultTheme {

    @ThemeVariant(id = "default-main_and_left_columns", regions = {"main", "left"})
    public static String getDefaultMainAndLeftColumnTemplate() {
        return "cmscore/themes/DefaultTheme/variant_main_and_left_columns.html";
    }

    @ThemeVariant(id = "default-three_columns", regions = {"main", "left", "right"})
    public static String getDefaultThreeColumnTemplate() {
        return "cmscore/themes/DefaultTheme/variant_three_columns.html";
    }

}

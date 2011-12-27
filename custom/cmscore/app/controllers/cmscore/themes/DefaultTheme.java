package controllers.cmscore.themes;

import play.modules.cmscore.annotations.Theme;
import play.modules.cmscore.annotations.ThemeVariant;

@Theme(id = "default")
public class DefaultTheme {

    @ThemeVariant(id = "default-main_and_left_columns", contentAreas = {"main", "left"})
    public static String getDefaultMainAndLeftColumnTemplate() {
        return "themes/DefaultTheme/main_and_left_columns.html";
    }

    @ThemeVariant(id = "default-three_columns", contentAreas = {"main", "left", "right"})
    public static String getDefaultThreeColumnTemplate() {
        return "themes/DefaultTheme/three_columns.html";
    }

}

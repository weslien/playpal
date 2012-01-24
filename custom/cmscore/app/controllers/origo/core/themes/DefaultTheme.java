package controllers.origo.core.themes;

import play.modules.origo.core.annotations.Theme;
import play.modules.origo.core.annotations.ThemeVariant;

@Theme(id = "default")
public class DefaultTheme {

    @ThemeVariant(id = "default-main_and_left_columns", regions = {"main", "left"})
    public static String getDefaultMainAndLeftColumnTemplate() {
        return "themes/origo/DefaultTheme/variant_main_and_left_columns.html";
    }

    @ThemeVariant(id = "default-three_columns", regions = {"main", "left", "right"})
    public static String getDefaultThreeColumnTemplate() {
        return "themes/origo/DefaultTheme/variant_three_columns.html";
    }

}

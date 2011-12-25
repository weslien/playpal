package controllers.themes.cmsdefault;

import play.modules.cmscore.annotations.Theme;
import play.modules.cmscore.annotations.ThemeVariant;

@Theme(id = "default")
public class DefaultTheme {
    
    @ThemeVariant(id = "default-main_and_left_columns", contentAreas = {"main", "left"})
    public String getDefaultMainAndLeftColumnTemplate() {
        return "themes/cmsdefault/main_and_left_columns.html";
    }

    @ThemeVariant(id = "default-three_columns", contentAreas = {"main", "left", "right"})
    public String getDefaultThreeColumnTemplate() {
        return "themes/cmsdefault/three_columns.html";
    }

}

package helpers;

import models.cmscore.Settings;
import models.cmscore.SettingsKeys;
import models.cmscore.navigation.BasicNavigation;
import play.Logger;

public class SettingsHelper {

    public static String getStartPage() {
        return Settings.load().getValue(SettingsKeys.Core.START_PAGE);
    }

    public static String getPageNotFoundPage() {
        return Settings.load().getValue(SettingsKeys.Core.PAGE_NOT_FOUND_PAGE);
    }

    public static String getInternalServerErrorPage() {
        return Settings.load().getValue(SettingsKeys.Core.INTERNAL_SERVER_ERROR_PAGE);
    }

    public static String getBaseUrl() {
        return Settings.load().getValue(SettingsKeys.Core.BASE_URL);
    }

    public static String getThemeVariant() {
        return Settings.load().getValue(SettingsKeys.Core.THEME_VARIANT);
    }

    public static Class getNavigationType() {
        String navigationType = Settings.load().getValue(SettingsKeys.Core.NAVIGATION_TYPE);
        if (navigationType != null) {
            try {
                return Class.forName(navigationType);
            } catch (ClassNotFoundException e) {
                Logger.error("Unable to find navigation type [" + navigationType + "], using system default navigation type instead");
            }
        }
        return BasicNavigation.class;
    }

}

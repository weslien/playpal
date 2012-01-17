package helpers;

import models.cmscore.Settings;
import models.cmscore.SettingsKeys;

public class SettingsHelper {

    public static String getStartPage() {
        return Settings.load().getValue(SettingsKeys.Core.START_PAGE);
    }

    public static String getPageNotFoundPage() {
        return Settings.load().getValue(SettingsKeys.Core.PAGE_NOT_FOUND_PAGE);
    }

    public static String getBaseUrl() {
        return Settings.load().getValue(SettingsKeys.Core.BASE_URL);
    }

    public static String getThemeVariant() {
        return Settings.load().getValue(SettingsKeys.Core.THEME_VARIANT);
    }

}

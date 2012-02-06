package origo.helpers;

import models.origo.core.Settings;
import models.origo.core.SettingsKeys;
import models.origo.core.navigation.BasicNavigation;
import play.Logger;

public class SettingsHelper {

    public static class Core {

        public static String getBaseUrl() {
            return Settings.load().getValue(SettingsKeys.Core.BASE_URL);
        }

        public static String getStartPage() {
            return Settings.load().getValue(SettingsKeys.Core.START_PAGE);
        }

        public static String getPageNotFoundPage() {
            return Settings.load().getValue(SettingsKeys.Core.PAGE_NOT_FOUND_PAGE);
        }

        public static String getInternalServerErrorPage() {
            return Settings.load().getValue(SettingsKeys.Core.INTERNAL_SERVER_ERROR_PAGE);
        }

        public static String getThemeVariant() {
            return Settings.load().getValue(SettingsKeys.Core.THEME_VARIANT);
        }

        public static String getNavigationType() {
            return SettingsHelper.getNavigationType(SettingsKeys.Core.NAVIGATION_TYPE);
        }

        public static String getDefaultFormProviderType() {
            return Settings.load().getValue(SettingsKeys.Core.DEFAULT_FORM_TYPE);
        }

        public static String getPostHandler() {
            return Settings.load().getValue(SettingsKeys.Core.POST_HANDLER);
        }

    }

    public static class Admin {

        public static String getThemeVariant() {
            return Settings.load().getValue(SettingsKeys.Admin.THEME_VARIANT);
        }

        public static String getNavigationType() {
            return SettingsHelper.getNavigationType(SettingsKeys.Admin.NAVIGATION_TYPE);
        }

        public static String getDashboardType() {
            return Settings.load().getValue(SettingsKeys.Admin.DASHBOARD_TYPE);
        }

        public static String getEditorType() {
            return Settings.load().getValue(SettingsKeys.Admin.RICHTEXT_EDITOR_TYPE);
        }

    }

    private static String getNavigationType(String settingName) {
        String navigationType = Settings.load().getValue(settingName);
        if (navigationType != null) {
            try {
                return Class.forName(navigationType).getName();
            } catch (ClassNotFoundException e) {
                Logger.error("Unable to find navigation type [" + navigationType + "], using system default navigation type instead");
            }
        }
        return BasicNavigation.class.getName();
    }

}

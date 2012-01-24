package models.origo.core;

public class SettingsKeys {

    // Private constructor to prevent instantation
    private SettingsKeys() {
        // No implementation
    }

    public static interface Core {
        public static final String BASE_URL = "base_url";
        public static final String START_PAGE = "start_page";
        public static final String PAGE_NOT_FOUND_PAGE = "page_not_found_page";
        public static final String INTERNAL_SERVER_ERROR_PAGE = "internal_server_error_page";

        public static final String THEME_VARIANT = "theme_variant";
        public static final String NAVIGATION_TYPE = "navigation_type";
    }

}

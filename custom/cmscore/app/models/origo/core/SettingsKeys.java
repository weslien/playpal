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
        public static final String DEFAULT_FORM_TYPE = "default_form_type";
        public static final String SUBMIT_HANDLER = "submit_handler";
    }

    public static interface Admin {
        public static final String THEME_VARIANT = "admin_theme_variant";
        public static final String NAVIGATION_TYPE = "admin_navigation_type";
        public static final String DASHBOARD_TYPE = "admin_dashboard_type";
        public static final String RICHTEXT_EDITOR_TYPE = "admin_editor_type";
    }
}

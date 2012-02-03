package controllers.origo.admin;

import models.origo.core.Settings;
import models.origo.core.SettingsKeys;
import org.apache.commons.lang.StringUtils;
import origo.listeners.DashboardAdminProvider;
import origo.listeners.TinyMCEEditorProvider;
import origo.themes.AdminTheme;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class BootStrap extends Job {

    public void doJob() {
        Settings settings = Settings.load();
        setValueIfMissing(settings, SettingsKeys.Admin.DASHBOARD_TYPE, DashboardAdminProvider.TYPE);
        setValueIfMissing(settings, SettingsKeys.Admin.THEME_VARIANT, AdminTheme.DEFAULT_VARIANT_NAME);
        setValueIfMissing(settings, SettingsKeys.Admin.RICHTEXT_EDITOR_TYPE, TinyMCEEditorProvider.EDITOR_TYPE);
        settings.save();
    }

    private void setValueIfMissing(Settings settings, String settingKey, String newValue) {
        if (StringUtils.isBlank(settings.getValue(settingKey))) {
            settings.setValue(settingKey, newValue);
        }
    }
}

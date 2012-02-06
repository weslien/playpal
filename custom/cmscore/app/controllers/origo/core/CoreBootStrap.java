package controllers.origo.core;

import models.origo.core.Settings;
import models.origo.core.SettingsKeys;
import org.apache.commons.lang.StringUtils;
import origo.listeners.DefaultFormProvider;
import origo.listeners.DefaultSubmitHandler;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class CoreBootStrap extends Job {

    public void doJob() {
        Settings settings = Settings.load();
        setValueIfMissing(settings, SettingsKeys.Core.DEFAULT_FORM_TYPE, DefaultFormProvider.TYPE);
        setValueIfMissing(settings, SettingsKeys.Core.POST_HANDLER, DefaultSubmitHandler.class.getName());
        settings.save();
    }

    private void setValueIfMissing(Settings settings, String settingKey, String newValue) {
        if (StringUtils.isBlank(settings.getValue(settingKey))) {
            settings.setValue(settingKey, newValue);
        }
    }

}

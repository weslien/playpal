package models.cmscore;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SettingsTest {
    
    @Test
    public void changingSettingsShouldWork() {
        Settings settings = Settings.load();
        settings.setValue("bla", "1.0");
        settings.save();
        Settings settingsAfter = Settings.load();
        double v = settingsAfter.getValueAsDouble("bla");
        assertEquals("Value returned is wrong", 1.0, v);
    }
    
    @Test(expected = RuntimeException.class)
    public void creatingNewSettingsShouldFail() {
        Settings shouldBeOnlySettings = Settings.load();
        Settings settings = new Settings();
        Settings.save(settings);
    }
    
}

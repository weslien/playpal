package models.cmscore;

import org.junit.Test;
import play.test.UnitTest;

public class SettingsTest extends UnitTest {

    @Test
    public void changingSettingsShouldWork() {
        Settings settings = Settings.load();
        settings.setValue("bla", "111");
        settings.save();
        Settings settingsAfter = Settings.load();
        Integer v = settingsAfter.getValueAsInteger("bla");
        assertNotNull("Value returned is null", v);
        assertEquals("Value returned is wrong", 111, v.intValue());
    }

    @Test(expected = RuntimeException.class)
    public void creatingNewSettingsShouldFail() {
        Settings shouldBeOnlySettings = Settings.load();
        Settings settings = new Settings();
        Settings.save(settings);
    }

}

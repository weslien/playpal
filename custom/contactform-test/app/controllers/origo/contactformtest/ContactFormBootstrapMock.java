package controllers.origo.contactformtest;

import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class ContactFormBootstrapMock extends Job {

    public void doJob() {
        Logger.info("Clearing database");
        Fixtures.deleteDatabase();
        Logger.info("Loading contactform-test/initial-data.yml");
        Fixtures.loadModels("initial-data.yml");
    }

}
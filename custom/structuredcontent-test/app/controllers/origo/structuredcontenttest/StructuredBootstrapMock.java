package controllers.origo.structuredcontenttest;

import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class StructuredBootstrapMock extends Job {

    public void doJob() {
        play.Logger.info("Clearing database");
        Fixtures.deleteDatabase();
        Logger.info("Loading structuredcontent-test/initial-data.yml");
        Fixtures.loadModels("initial-data.yml");
    }

}
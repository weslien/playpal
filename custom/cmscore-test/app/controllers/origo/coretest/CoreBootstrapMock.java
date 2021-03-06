package controllers.origo.coretest;

import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class CoreBootstrapMock extends Job {

    public void doJob() {
        play.Logger.info("Clearing database");
        Fixtures.deleteDatabase();
        Logger.info("Loading cmscore-test/initial-data.yml");
        Fixtures.loadModels("initial-data.yml");
    }

}
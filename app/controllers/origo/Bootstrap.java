package controllers.origo;

import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        if (Play.mode == Play.Mode.DEV) {
            Logger.info("Clearing database");
            Fixtures.deleteDatabase();
            Logger.info("Loading initial-data.yml");
            Fixtures.loadModels("initial-data.yml");
        }
    }
}

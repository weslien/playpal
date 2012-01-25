package controllers.origo.core;

import models.origo.core.RootNode;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        loadInitialData();
    }

    private void loadInitialData() {
        // TODO: remove the Fixtures.deleteDatabase call
        Logger.info("Clearing database");
        Fixtures.deleteDatabase();
        // Check if the database is empty
        if (RootNode.count() == 0) {
            Logger.info("Loading cmscore/initial-data.yml");
            Fixtures.loadModels("initial-data.yml");
        }
    }

}
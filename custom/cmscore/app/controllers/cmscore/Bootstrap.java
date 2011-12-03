package controllers.cmscore;

import models.cmscore.Leaf;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        Fixtures.deleteDatabase();
        // Check if the database is empty
        if(Leaf.count() == 0) {
            Fixtures.loadModels("initial-data.yml");
        }
    }

}
package controllers.cmscore;

import models.cmscore.RootLeaf;
import org.apache.log4j.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

    private static final Logger LOG = Logger.getLogger(Bootstrap.class);

    public void doJob() {
        // TODO: remove the deleteDatabase call
        LOG.info("Clearing database");
        Fixtures.deleteDatabase();
        // Check if the database is empty
        if(RootLeaf.count() == 0) {
            LOG.info("Loading initial-data.yml");
            Fixtures.loadModels("initial-data.yml");
        }
    }

}
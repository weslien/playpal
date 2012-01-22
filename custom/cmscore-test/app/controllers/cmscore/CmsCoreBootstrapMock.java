package controllers.cmscore;

import org.apache.log4j.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class CmsCoreBootstrapMock extends Job {

    private static final Logger LOG = Logger.getLogger(CmsCoreBootstrapMock.class);

    public void doJob() {
        LOG.info("Clearing database");
        Fixtures.deleteDatabase();
        LOG.info("Loading initial-data.yml");
        Fixtures.loadModels("initial-data.yml");
    }

}
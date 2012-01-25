package controllers.origo.coretest;

import org.apache.log4j.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class CoreBootstrapMock extends Job {

    private static final Logger LOG = Logger.getLogger(CoreBootstrapMock.class);

    public void doJob() {
        play.Logger.info("Clearing database");
        Fixtures.deleteDatabase();
        LOG.info("Loading cmscore-test/initial-data.yml");
        Fixtures.loadModels("initial-data.yml");
    }

}
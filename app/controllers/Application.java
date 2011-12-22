package controllers;

import controllers.cmscore.CoreController;
import models.cmscore.Settings;
import models.cmscore.SettingsKeys;
import play.modules.cmscore.LeafType;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        //TODO: Check for if config !exists and redirect to wizard

        //TODO: Display front page
        Settings settings = Settings.load();
        String startPage = settings.getValue(SettingsKeys.Core.START_PAGE);

        LeafType leafType = CoreController.index(startPage);
        renderArgs.put("leaf", leafType);
        render(leafType.getTemplate());
    }

}
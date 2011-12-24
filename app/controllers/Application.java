package controllers;

import controllers.cmscore.CoreController;
import models.cmscore.Settings;
import models.cmscore.SettingsKeys;
import play.modules.cmscore.RenderedLeaf;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        //TODO: Check for if config !exists and redirect to wizard

        Settings settings = Settings.load();
        String startPage = settings.getValue(SettingsKeys.Core.START_PAGE);

        RenderedLeaf leaf = CoreController.getPage(startPage);
        renderArgs.put("leaf", leaf);
        render(leaf.getTemplate());
    }

}
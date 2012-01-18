package models.cmscore.navigation;

import helpers.SettingsHelper;
import models.cmscore.Alias;
import models.cmscore.Page;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;

@Entity
public class PageIdNavigation extends Model {

    @Required
    @Unique
    public String identifier;

    @Required
    public String pageId;

    public String getLink() {
        Collection<Alias> aliases = Alias.findWithPageId(pageId);
        if (aliases == null || aliases.isEmpty()) {
            if (SettingsHelper.getStartPage().equals(pageId)) {
                return SettingsHelper.getBaseUrl();
            }
            return SettingsHelper.getBaseUrl() + Page.findCurrentVersion(pageId, new Date()).getNodeId();
        } else {
            Alias alias = aliases.iterator().next();
            return SettingsHelper.getBaseUrl() + alias.path;
        }
    }

    public static PageIdNavigation findWithIdentifier(String identifier) {
        return PageIdNavigation.find("select distinct n from PageIdNavigation n where identifier=:identifier").
                bind("identifier", identifier).
                first();
    }
}

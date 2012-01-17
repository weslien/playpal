package models.cmscore.navigation;

import helpers.SettingsHelper;
import models.cmscore.Alias;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Collection;

@Entity
public class AliasNavigation extends Model {

    @Required
    @Unique
    public String identifier;

    // TODO: It would be better to have alias point to the id of an Alias instead (but that is really hard with yaml)
    @Required
    public String alias;

    public String getLink() {
        Alias aliasModel = Alias.findWithPath(alias);
        if (aliasModel != null && SettingsHelper.getStartPage().equals(aliasModel.pageId)) {
            return SettingsHelper.getBaseUrl();
        }
        return SettingsHelper.getBaseUrl() + alias;
    }

    public static AliasNavigation findWithIdentifier(String identifier) {
        Collection<AliasNavigation> aliases = AliasNavigation.findAll();
        return AliasNavigation.find("select distinct n from AliasNavigation n where identifier=:identifier").
                bind("identifier", identifier).
                first();
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("AliasNavigation {").
                append("identifier='").append(identifier).append('\'').
                append(", alias='").append(alias).append('\'').
                append('}').
                toString();
    }
}

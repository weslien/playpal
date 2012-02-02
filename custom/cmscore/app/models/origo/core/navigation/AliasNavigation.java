package models.origo.core.navigation;

import models.origo.core.Alias;
import origo.helpers.SettingsHelper;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Entity;

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
        if (aliasModel != null && SettingsHelper.Core.getStartPage().equals(aliasModel.pageId)) {
            return SettingsHelper.Core.getBaseUrl();
        }
        return SettingsHelper.Core.getBaseUrl() + alias;
    }

    public static AliasNavigation findWithIdentifier(String identifier) {
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

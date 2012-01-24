package models.origo.core.navigation;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class ExternalLinkNavigation extends Model {

    @Required
    @Unique
    public String identifier;

    @Required
    public String title;

    @Required
    public String link;

    public String getLink() {
        return link;
    }

    public static ExternalLinkNavigation findWithIdentifier(String identifier) {
        return ExternalLinkNavigation.find("select distinct n from ExternalLinkNavigation n where identifier=:identifier").
                bind("identifier", identifier).
                first();
    }
}

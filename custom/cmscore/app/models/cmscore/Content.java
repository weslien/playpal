package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Content extends Model {

    @Required
    public String identifier;
    
    @Required
    @Column(name = "content")
    @Lob
    public String value;

    public static Content findByIdentifier(String identifier) {
        return Content.find("select distinct c from Content c where identifier=:identifier").
                bind("identifier", identifier).
                first();
    }
    
}

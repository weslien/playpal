package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.cmscore.Renderable;
import play.modules.cmscore.annotations.UIElementType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Content extends Model implements Renderable {

    @Required
    public String identifier;
    
    @Required
    @Column(name = "content")
    @Lob
    public String value;

    @Override
    public String getContent() {
        return value;
    }

    public UIElementType getType() {
        return UIElementType.TEXT;
    }

    public static Content findByIdentifier(String identifier) {
        return Content.find("select distinct c from Content c where identifier=:identifier").
                bind("identifier", identifier).
                first();
    }
    
}

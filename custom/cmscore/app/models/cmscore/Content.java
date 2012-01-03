package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Content extends Model {

    @Required
    @Column(name = "content")
    @Lob
    public String value;

}

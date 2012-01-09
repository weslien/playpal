package models.cmscore;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Alias extends Model {

    @Required
    @Unique
    public String path;

    @Required
    public String page;

    public Alias(String path, String page) {
        this.path = path;
        this.page = page;
    }

    public static Alias findWithPath(String path) {
        return Alias.find("select a from Alias a where path=:path").bind("path", path).first();
    }

    @Override
    public String toString() {
        return "Alias{" +
                "path='" + path + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}

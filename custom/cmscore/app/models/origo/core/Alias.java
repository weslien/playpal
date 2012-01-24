package models.origo.core;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Collection;

@Entity
public class Alias extends Model {

    @Required
    @Unique
    public String path;

    @Required
    public String pageId;

    public Alias(String path, String pageId) {
        this.path = path;
        this.pageId = pageId;
    }

    public static Alias findWithId(long id) {
        return Alias.findById(id);
    }

    public static Alias findWithPath(String path) {
        return Alias.find("select a from Alias a where path=:path").bind("path", path).first();
    }

    public static Collection<Alias> findWithPageId(String pageId) {
        return Alias.find("select a from Alias a where pageId=:pageId").bind("pageId", pageId).fetch();
    }

    public static Alias findFirstAliasForPageId(String pageId) {
        Collection<Alias> aliases = Alias.findWithPageId(pageId);
        if (aliases == null || aliases.isEmpty()) {
            return null;
        } else {
            return aliases.iterator().next();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Alias {").
                append("path='").append(path).append('\'').
                append(", page='").append(pageId).append('\'').
                append('}').
                toString();
    }
}

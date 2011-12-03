package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.List;
import java.util.UUID;

@Entity
public class Leaf extends Model {

    @Required
    public String uuid;
    
    @Required
    public String title;

    @Required
    public Integer version;

    public Leaf(Integer version, String title) {
        this.uuid = UUID.randomUUID().toString();
        this.title = title;
        this.version = version;
    }
    
    public Leaf(String uuid, Integer version, String title) {
        this.uuid = uuid;
        this.title = title;
        this.version = version;
    }
    
    public static Leaf findWithUuidAndVersion(String uuid, Integer version) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uuid and l.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
    }

    public static List<Leaf> findWithUid(String uuid) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uid"
        ).bind("uid", uuid).fetch();
    }

    public String toString() {
        return "(" + uuid + "," + version + ") - " +title;
    }
}

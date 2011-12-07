package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Leaf extends Model {

    @Required
    public String uuid;
    
    @Required
    public String title;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    public Long version;

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date publish;

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date unPublish;

    public Leaf(Long version, String title) {
        this.uuid = UUID.randomUUID().toString();
        this.title = title;
        this.version = version;
    }
    
    public Leaf(String uuid, Long version, String title) {
        this.uuid = uuid;
        this.title = title;
        this.version = version;
    }
    
    public static List<Leaf> findAllCurrentVersions(Date today) {
        return Leaf.find(
                "select l from Leaf l " +
                "where l.version = (" +
                        "select max(l2.version) from Leaf l2 " +
                        "where l2.uuid = l.uuid and " +
                        "(l2.publish = null or l2.publish < :today) and " +
                        "(l2.unPublish = null OR l2.unPublish >= :today)" +
                ")"
        ).bind("today", today).fetch();
    }
    
    public static Leaf findWithUuidSpecificVersion(String uuid, Long version) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uuid and " +
                "l.version = :version"
        ).bind("uuid", uuid).bind("version", version).first();
    }

    public static Leaf findWithUuidLatestPublishedVersion(String uuid, Date today) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uuid and " +
                "(l.publish = null or l.publish < :today) and " +
                "(l.unPublish = null OR l.unPublish >= :today)" +
                "order by version desc"
        ).bind("uuid", uuid).bind("today", today).first();
    }

    public static List<Leaf> findWithUuidAllVersions(String uuid) {
        return Leaf.find(
                "select distinct l from Leaf l " +
                "where l.uuid = :uuid"
        ).bind("uuid", uuid).fetch();
    }

    public String toString() {
        return "(" + uuid + "," + version + ") - " +title;
    }
}

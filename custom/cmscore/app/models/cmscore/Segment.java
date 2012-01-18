package models.cmscore;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.Collection;

@Entity
public class Segment extends Model {

    @Required
    public String nodeId;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    public Long version;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    public Long weight;

    @Required
    public String region;

    @Required
    public String type;

    @Required
    public String referenceId;

    public Class getTypeClass() {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find the class for segment type [" + type + "]: " + e.getMessage(), e);
        }
    }

    public static Collection<Segment> findWithUuidSpecificVersion(String nodeId, Long version) {
        return Segment.
                find("select distinct b from Segment b where b.nodeId = :nodeId and b.version = :version").
                bind("nodeId", nodeId).
                bind("version", version).
                fetch();
    }

}

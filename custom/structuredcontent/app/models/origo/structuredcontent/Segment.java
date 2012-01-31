package models.origo.structuredcontent;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Segment extends Model {

    @Required
    public String nodeId;

    // TODO: Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3 (2.0?)
    @Required
    public Long version;

    @Required
    public String type;

    @Required
    public String referenceId;

    public static List<Segment> findWithNodeIdAndSpecificVersion(String nodeId, Long version) {
        return Segment.
                find("select distinct s from Segment s where s.nodeId = :nodeId and s.version = :version").
                bind("nodeId", nodeId).
                bind("version", version).
                fetch();
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Segment {").
                append("nodeId='").append(nodeId).append("', ").
                append("version=").append(version).append(", ").
                append('}').toString();
    }
}

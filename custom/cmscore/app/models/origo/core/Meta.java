package models.origo.core;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;

@Entity
public class Meta extends Model {

    @Required
    public String nodeId;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    public Long version;

    @Required
    public String referenceId;

    // Should only have to be Integer but because of defect #521 in play that doesn't work. Should be fixed in 1.3
    @Required
    public Long weight;

    @Required
    public String region;

    public static Meta findWithUuidSpecificVersion(String nodeId, Long version, String referenceId) {
        return Meta.
                find("select distinct m from Meta m where m.nodeId = :nodeId and m.version = :version and m.referenceId = :referenceId").
                bind("nodeId", nodeId).
                bind("version", version).
                bind("referenceId", referenceId).
                first();
    }

    // TODO: Make this loaded from the database instead
    public static Meta defaultMeta() {
        Meta meta = new Meta();
        meta.region = "main";
        meta.weight = 100l;
        return meta;
    }
}

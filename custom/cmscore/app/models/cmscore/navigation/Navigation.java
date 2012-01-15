package models.cmscore.navigation;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

@Entity
public class Navigation extends Model {

    @ManyToOne
    public Navigation parent;

    @OneToMany
    public List<Navigation> children;

    @Required
    public String type;

    @Required
    public String section;

    @Required
    public String referenceId;

    public Class getTypeClass() {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find the class for navigation type [" + type + "]: " + e.getMessage(), e);
        }
    }

    public static Collection<Navigation> findTopWithSection(String section, Navigation parent) {
        String query = "select distinct n from Navigation n where n.section=:section and ";
        if (parent != null) {
            return Navigation.find(query + "parent=:parent").
                    bind("section", section).
                    bind("parent", parent).
                    fetch();
        } else {
            return Navigation.find(query + "parent is null").
                    bind("section", section).
                    fetch();
        }
    }
}

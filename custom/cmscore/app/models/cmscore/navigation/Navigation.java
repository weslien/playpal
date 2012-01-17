package models.cmscore.navigation;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class Navigation extends Model implements Comparable<Navigation> {

    @ManyToOne
    public Navigation parent;

    @OneToMany
    public Set<Navigation> children;

    @Required
    public String type;

    @Required
    public String section;

    @Required
    public String referenceId;

    @Required
    @Column(name = "sortOrder")
    public int order;

    public Class getTypeClass() {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to find the class for navigation type [" + type + "]: " + e.getMessage(), e);
        }
    }

    public static Collection<Navigation> findTopWithSection(String section, Navigation parent) {
        String query = "select distinct n from Navigation n where n.section=:section and ";
        List<Navigation> navigations;
        if (parent != null) {
            navigations = Navigation.find(query + "parent=:parent").
                    bind("section", section).
                    bind("parent", parent).

                    fetch();
        } else {
            navigations = Navigation.find(query + "parent is null").
                    bind("section", section).
                    fetch();
        }
        Collections.sort(navigations);
        return navigations;
    }

    @Override
    public int compareTo(Navigation navigation) {
        return new Integer(navigation.order).compareTo(order);
    }
}

package models.origo.core.navigation;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.origo.core.Navigation;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

@Entity
public class BasicNavigation extends Model implements Navigation<BasicNavigation>, Comparable<BasicNavigation> {

    @ManyToOne
    public BasicNavigation parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    public List<BasicNavigation> children;

    @Required
    public String type;

    @Required
    public String section;

    @Required
    public String referenceId;

    @Required
    public int weight;

    @Override
    public String getReferenceId() {
        return referenceId;
    }

    @Override
    public String getSection() {
        return section;
    }

    @Override
    public BasicNavigation getParent() {
        return parent;
    }

    @Override
    public List<BasicNavigation> getChildren() {
        return children;
    }

    public static List<BasicNavigation> findWithSection(String section) {
        List<BasicNavigation> navigations =
                BasicNavigation.find("select distinct n from BasicNavigation n where n.section=:section and parent is null").
                        bind("section", section).
                        fetch();
        Collections.sort(navigations);
        return navigations;
    }

    public static List<BasicNavigation> findWithSection(String section, BasicNavigation parent) {
        List<BasicNavigation> navigations =
                BasicNavigation.
                        find("select distinct n from BasicNavigation n where n.section=:section and parent=:parent").
                        bind("section", section).
                        bind("parent", parent).
                        fetch();
        Collections.sort(navigations);
        return navigations;
    }

    public static List<BasicNavigation> findWithSection(String section, String parentId) {
        List<BasicNavigation> navigations =
                BasicNavigation.
                        find("select distinct n from BasicNavigation n where n.section=:section and parent=:parentId").
                        bind("section", section).

                        fetch();
        Collections.sort(navigations);
        return navigations;
    }

    @Override
    public int compareTo(BasicNavigation navigation) {
        return new Integer(weight).compareTo(navigation.weight);
    }

}

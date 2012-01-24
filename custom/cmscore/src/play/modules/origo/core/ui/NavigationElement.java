package play.modules.origo.core.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NavigationElement {

    // Plugins can define their own sections, for example an Intranet module could define a private navigation section
    public final static String FRONT = "front";
    public final static String ADMIN = "admin";

    public String section;
    public String title;
    public String link;

    public List<NavigationElement> children;

    public boolean selected;
    public Set<String> styleClasses;

    public NavigationElement(String section, String title, String link) {
        this.section = section;
        this.title = title;
        this.link = link;
        children = new ArrayList<NavigationElement>();
    }

    public NavigationElement(String section, String title, String link, boolean selected) {
        this.section = section;
        this.title = title;
        this.link = link;
        this.selected = selected;
        children = new ArrayList<NavigationElement>();
    }
}

package play.modules.cmscore.ui;

public class NavigationElement {

    // Plugins can define their own sections, for example an Intranet module could define a private navigation section
    public final static String FRONT = "front";
    public final static String ADMIN = "admin";

    public String section;
    public String title;
    public String link;

    public NavigationElement(String section, String title, String link) {
        this.section = section;
        this.title = title;
        this.link = link;
    }
}

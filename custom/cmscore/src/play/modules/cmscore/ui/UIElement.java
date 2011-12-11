package play.modules.cmscore.ui;

/**
 * Created by IntelliJ IDEA.
 * User: gustav
 * Date: 2011-12-11
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class UIElement {
    
    private int weight;
    
    private String title;
    
    private String id;
    
    private String body;

    public UIElement(int weight, String title, String id, String body) {
        this.weight = weight;
        this.title = title;
        this.id = id;
        this.body = body;
    }

    public int getWeight() {
        return this.weight;  //To change body of created methods use File | Settings | File Templates.
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

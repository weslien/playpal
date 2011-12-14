package play.modules.cmscore.ui;

public class UIElement {

    private String id;

    private String title;

    private String body;

    private int weight;

    public UIElement(String id, String title, String body, int weight) {
        this.weight = weight;
        this.title = title;
        this.id = id;
        this.body = body;
    }

    public int getWeight() {
        return this.weight;
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

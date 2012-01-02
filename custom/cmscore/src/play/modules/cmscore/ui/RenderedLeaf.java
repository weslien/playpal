package play.modules.cmscore.ui;

import java.util.Map;

public class RenderedLeaf {

    private String id;

    private String template;

    private String title;

    private String meta;

    private String script;

    private Map<String, String> region;

    public RenderedLeaf() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Map<String, String> getRegion() {
        return region;
    }

    public void setRegion(Map<String, String> region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "RenderedLeaf (" + id + ", template=" + template + ") - " + title;
    }
}

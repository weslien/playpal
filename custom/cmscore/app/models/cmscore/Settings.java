package models.cmscore;

import org.apache.commons.lang.BooleanUtils;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Settings extends Model {

    @MapKeyColumn(name = "name")
    @ElementCollection
    @JoinTable(name = "settings_values")
    @Column(name = "value")
    private Map<String, String> values = new HashMap<String, String>();

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(final Map<String, String> values) {
        this.values = values;
    }

    public boolean containsKey(final String key) {
        return getValues().containsKey(key);
    }

    public void setValue(final String key, final String value) {
        getValues().put(key, value);
    }

    public String getValue(final String key) {
        return getValues().get(key);
    }

    /*
     * Getting general settings based on name
     */

    public Integer getValueAsInteger(final String name) {
        final String value = getValues().get(name);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return null;
    }

    public Long getValueAsLong(final String name) {
        final String value = getValues().get(name);
        if (value != null) {
            return Long.parseLong(value);
        }
        return null;
    }

    public Boolean getValueAsBoolean(String name) {
        final String value = getValues().get(name);
        if (value != null) {
            return BooleanUtils.toBoolean(value);
        }
        return null;
    }

    public Double getValueAsDouble(final String name) {
        final String value = getValues().get(name);
        if (value != null) {
            return Double.parseDouble(value);
        }
        return null;
    }

    public Float getValueAsFloat(final String name) {
        final String value = getValues().get(name);
        if (value != null) {
            return Float.parseFloat(value);
        }
        return null;
    }

    private static boolean hasSettingsStored() {
        return loadQuery().first() != null;
    }

    private static JPAQuery loadQuery() {
        return Settings.find("select s from Settings s");
    }

    private static Settings doSave(Settings settings) {
        return settings.save();
    }

    public static Settings load() {
        if (hasSettingsStored()) {
            return loadQuery().first();
        } else {
            return doSave(new Settings());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static Settings save(Settings settings) {
        return settings.save();
    }

    @Override
    public Settings save() {
        if (hasSettingsStored()) {
            if (!load().getId().equals(getId())) {
                throw new RuntimeException("Only one instance of setting should be available in the system");
            }
        }
        return super.save();
    }

    @Override
    public boolean create() {
        return hasSettingsStored() || super.create();
    }
}

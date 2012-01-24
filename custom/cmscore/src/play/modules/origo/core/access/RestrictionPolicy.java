package play.modules.origo.core.access;

/**
 * Defines a restriction policy to be used in core components as well as custom modules to
 * advertise custom policies to the access system.
 * User: gustav
 * Date: 2011-12-07
 * Time: 20:16
 * To change this template use File | Settings | File Templates.
 */
public final class RestrictionPolicy {
    private final String key;
    private final String name;

    public RestrictionPolicy(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

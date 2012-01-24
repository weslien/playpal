package play.modules.origo.core;

import java.util.Collection;

public interface Navigation<T> {

    String getReferenceId();

    String getSection();

    T getParent();

    Collection<T> getChildren();

}

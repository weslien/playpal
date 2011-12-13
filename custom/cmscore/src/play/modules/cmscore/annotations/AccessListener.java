package play.modules.cmscore.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Lets the module supply the play.modules.cmscore.access restriction subsystem with its own list of restrictions.
 * Implementing methods should take no parmeters and return a <code>Set<RestrictionPolicy></code>
 * that contains all custom restrictions that this module offers/requires
 * User: gustav
 * Date: 2011-12-07
 * Time: 20:14
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessListener {
}

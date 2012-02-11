package play.modules.origo.admin;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Admins {

    public static Set<String> pages = new HashSet<String>();
    public static Map<String, String> aliases = new HashMap<String, String>();

    public static void invalidate() {
        pages.clear();
        aliases.clear();
    }

    public static String getSafeString(String name) {
        if (name != null) {
            String trimmedValue = StringUtils.trim(name);
            return trimmedValue.replaceAll("/", "_");
        }
        return "";
    }

    public static String getAliasForPageName(String page) {
        for (String alias : aliases.keySet()) {
            String aliasPage = aliases.get(alias);
            if (aliasPage.equals(page)) {
                return alias;
            }
        }
        return null;
    }

}

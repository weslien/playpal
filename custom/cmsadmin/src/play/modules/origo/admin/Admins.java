package play.modules.origo.admin;

import org.apache.commons.lang.StringUtils;
import play.modules.origo.admin.annotations.Admin;
import play.modules.origo.core.annotations.Provides;

import java.lang.reflect.Method;
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

    public static void addPage(Method m) {
        Admin.Page pageAnnotation = m.getAnnotation(Admin.Page.class);
        if (StringUtils.isBlank(pageAnnotation.name())) {
            throw new RuntimeException("Admin.Page can not have an empty name attribute");
        }
        String pageName = getSafeString(pageAnnotation.name());
        if (pages.contains(pageName)) {
            throw new RuntimeException("Admin.Page must have a unique name attribute");
        }

        Provides providesAnnotation = m.getAnnotation(Provides.class);
        if (providesAnnotation == null) {
            throw new RuntimeException("Methods annotated with Admin.Page must also be annotated with a @Provides annotation");
        }

        pages.add(pageName);
        if (StringUtils.isBlank(pageAnnotation.alias())) {
            aliases.put(pageAnnotation.name(), pageName);
        } else {
            aliases.put(pageAnnotation.alias(), pageName);
        }
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

package controllers.cmscore.fragments;

import play.Play;
import play.data.validation.Validation;
import play.exceptions.PlayException;
import play.exceptions.TemplateNotFoundException;
import play.mvc.Http;
import play.mvc.Scope;
import play.templates.Template;
import play.templates.TemplateLoader;

import java.util.HashMap;
import java.util.Map;

public class FragmentLoader {

    private static final String FRAGMENT_PREFIX = "fragments/";

    public static String loadHtmlFragment(String fragmentName, Map<String, String> attributes) {

        // Template datas
        Scope.RenderArgs templateBinding = Scope.RenderArgs.current();
        templateBinding.data.putAll(attributes);
        templateBinding.put("session", Scope.Session.current());
        templateBinding.put("request", Http.Request.current());
        templateBinding.put("flash", Scope.Flash.current());
        templateBinding.put("params", Scope.Params.current());
        templateBinding.put("errors", Validation.errors());
        try {
            Template template = TemplateLoader.load(template(fragmentName));
            HashMap<String, Object> args = new HashMap<String, Object>();
            args.put("attributes", attributes);
            return template.render(args);
        } catch (TemplateNotFoundException ex) {
            if (ex.isSourceAvailable()) {
                throw ex;
            }
            StackTraceElement element = PlayException.getInterestingStrackTraceElement(ex);
            if (element != null) {
                throw new TemplateNotFoundException(fragmentName, Play.classes.getApplicationClass(element.getClassName()), element.getLineNumber());
            } else {
                throw ex;
            }
        }

    }

    /**
     * Work out the default template to load for the action.
     * E.g. "controllers.Pages.index" returns "views/Pages/index.html".
     * @param fragmentName
     * @return correct
     */
    protected static String template(String fragmentName) {
        final Http.Request theRequest = Http.Request.current();
        final String format = theRequest.format;
        String templateName = FRAGMENT_PREFIX +fragmentName.replace(".", "/") + "." + (format == null ? "html" : format);
        if (templateName.startsWith("@")) {
            templateName = templateName.substring(1);
            if (!templateName.contains(".")) {
                templateName = theRequest.controller + "." + templateName;
            }
            templateName = templateName.replace(".", "/") + "." + (format == null ? "html" : format);
        }
        return templateName;
    }

}

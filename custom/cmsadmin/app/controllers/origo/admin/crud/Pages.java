package controllers.origo.admin.crud;

import controllers.CRUD;
import models.origo.core.BasicPage;
import play.exceptions.TemplateNotFoundException;

import java.util.Date;
import java.util.List;

@CRUD.For(BasicPage.class)
public class Pages extends CRUD {

    public static void list(int page, String search, String searchFields, String orderBy, String order) {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        if (page < 1) {
            page = 1;
        }
        List<BasicPage> objects = BasicPage.findAllCurrentVersions(new Date());
        Long count = type.count(search, searchFields, (String) request.args.get("where"));
        Long totalCount = type.count(null, null, (String) request.args.get("where"));
        try {
            render(type, objects, count, totalCount, page, orderBy, order);
        } catch (TemplateNotFoundException e) {
            render("CRUD/list.html", type, objects, count, totalCount, page, orderBy, order);
        }
    }

}

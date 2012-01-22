package controllers.cmscore;

import org.junit.Test;
import play.mvc.Http;
import play.test.FunctionalTest;

public class CmsCoreApplicationTest extends FunctionalTest {

    @Test
    public void indexPageWorks() {
        Http.Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

    @Test
    public void redirectedToPageNotFound() {
        Http.Response response = GET("/page-that-does-not-exist");
        assertStatus(302, response);
    }

    @Test
    public void thirdPageWorks() {
        Http.Response response = GET("/third");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        assertContentMatch("Flobble da woggle-shnozzle", response);
    }

    @Test
    public void programaticallyAddedContent() {
        Http.Response response = GET("/third");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
        assertContentMatch("Bla bla", response);
    }


}

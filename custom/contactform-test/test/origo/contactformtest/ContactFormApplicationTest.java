package origo.contactformtest;

import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ContactFormApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }

}
package origo.listeners;

public class PageNotFoundException extends RuntimeException {

    public final String pageUuid;

    public PageNotFoundException(String pageUuid) {
        this.pageUuid = pageUuid;
    }

}

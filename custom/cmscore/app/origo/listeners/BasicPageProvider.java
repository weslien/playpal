package origo.listeners;


import models.origo.core.BasicPage;
import models.origo.core.Content;
import models.origo.core.RootNode;
import org.apache.commons.lang.StringUtils;
import play.modules.origo.core.Node;
import play.modules.origo.core.annotations.OnLoad;
import play.modules.origo.core.annotations.Provides;
import play.modules.origo.core.ui.UIElement;

public class BasicPageProvider {

    @Provides(type = "node", with = "models.origo.core.BasicPage")
    public static BasicPage loadPage(RootNode rootNode) {

        BasicPage page = BasicPage.findWithNodeIdAndSpecificVersion(rootNode.nodeId, rootNode.version);
        if (page == null) {
            throw new PageNotFoundException(rootNode.nodeId);
        }
        page.rootNode = rootNode;

        return page;
    }

    @OnLoad(type = "node", with = "models.origo.core.BasicPage")
    public static void loadContent(Node node) {
        node.addUIElement(loadContent(((BasicPage) node).getLeadReferenceId()));
        node.addUIElement(loadContent(((BasicPage) node).getBodyReferenceId()));
    }

    public static UIElement loadContent(String referenceId) {
        if (!StringUtils.isBlank(referenceId)) {
            Content content = Content.findWithIdentifier(referenceId);
            if (content != null) {
                return new UIElement(content.identifier, UIElement.TEXT, content.value);
            }
        }
        //TODO: Handle this somehow, in dev/admin maybe show a UIElement with a warning message and in prod swallow error?
        return null;
    }

}

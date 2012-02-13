package origo.listeners;

import models.origo.core.RootNode;
import origo.helpers.forms.FormHelper;
import play.modules.origo.core.annotations.forms.OnSubmit;
import play.mvc.Scope;

public class RootNodeAdminProvider {

    @OnSubmit // TODO: Probably should be move to validation when that is ready
    public static void storeNode(Scope.Params params) {
        String nodeId = FormHelper.getNodeId(params);
        Long version = FormHelper.getNodeVersion(params);
        RootNode oldRootNode = RootNode.findWithNodeIdAndSpecificVersion(nodeId, version);
        if (oldRootNode == null) {
            throw new RuntimeException("Root node with id=\'" + nodeId + "\' does not exist");
        }

        RootNode oldNodeVersion = RootNode.findLatestVersionWithNodeId(nodeId);
        if (!oldNodeVersion.version.equals(version)) {
            throw new RuntimeException("Root node with id=\'" + nodeId + "\' and version =\'" + version + "\' has a newer version stored.");
        }
    }

}

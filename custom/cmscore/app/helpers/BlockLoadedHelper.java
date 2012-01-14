package helpers;

import models.cmscore.Block;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.BlockLoaded;
import play.modules.cmscore.ui.UIElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockLoadedHelper {

    public static void triggerBeforeListener(Class type, Leaf leaf, Block block) {
        List<CachedAnnotation> listeners = findListenerForType(type, false);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Leaf.class, leaf);
            parameters.put(Block.class, block);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    public static void triggerAfterListener(Class type, Leaf leaf, Block block, UIElement uiElement) {
        List<CachedAnnotation> listeners = findListenerForType(type, true);
        if (listeners != null && !listeners.isEmpty()) {
            Map<Class, Object> parameters = new HashMap<Class, Object>();
            parameters.put(Leaf.class, leaf);
            parameters.put(Block.class, block);
            parameters.put(UIElement.class, uiElement);
            for (CachedAnnotation listener : listeners) {
                ReflectionHelper.invokeMethod(listener.method, parameters);
            }
        }
    }

    private static List<CachedAnnotation> findListenerForType(final Class type, final boolean after) {
        return Listeners.getListenersForAnnotationType(BlockLoaded.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                BlockLoaded annotation = ((BlockLoaded) listener.annotation);
                return (annotation.type().equals(type) || annotation.type().equals(Object.class)) && annotation.after() == after;
            }
        });
    }
}

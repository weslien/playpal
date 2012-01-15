package helpers;

import models.cmscore.Block;
import models.cmscore.RootLeaf;
import play.modules.cmscore.CachedAnnotation;
import play.modules.cmscore.Leaf;
import play.modules.cmscore.Listeners;
import play.modules.cmscore.annotations.Provides;
import play.modules.cmscore.annotations.ProvidesType;
import play.modules.cmscore.ui.UIElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper to trigger \@Provides listeners. Should not be used directly, use LeafHelper instead.
 * @see LeafHelper
 * @see Provides
 */
public class ProvidesHelper {

    public static Leaf triggerLeafListener(Class withType, RootLeaf rootLeaf) {
        CachedAnnotation listener = findListener(ProvidesType.LEAF, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Leaf.class, rootLeaf);
        return (Leaf) ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    public static UIElement triggerBlockListener(Class withType, Leaf leaf, Block block) {
        CachedAnnotation listener = findListener(ProvidesType.BLOCK, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Leaf.class, leaf);
        parameters.put(Block.class, block);
        return (UIElement)ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    public static UIElement triggerFormListener(Class withType, Leaf leaf) {
        CachedAnnotation listener = findListener(ProvidesType.FORM, withType);
        Map<Class, Object> parameters = new HashMap<Class, Object>();
        parameters.put(Leaf.class, leaf);
        return (UIElement)ReflectionHelper.invokeMethod(listener.method, parameters);
    }

    private static CachedAnnotation findListener(ProvidesType type, Class withType) {
        CachedAnnotation listener = findListenerForType(type, withType);
        if (listener == null) {
            throw new RuntimeException("Every type (attribute 'with') must have a class annotated with @Provides to instantiate an instance");
        }
        return listener;
    }

    private static CachedAnnotation findListenerForType(final ProvidesType type, final Class withType) {
        List<CachedAnnotation> listeners = Listeners.getListenersForAnnotationType(Provides.class, new Listeners.ListenerSelector() {
            @Override
            public boolean isCorrectListener(CachedAnnotation listener) {
                Provides annotation = (Provides) listener.annotation;
                return annotation.type().equals(type) && annotation.with().equals(withType);
            }
        });
        if (!listeners.isEmpty()) {
            if (listeners.size() > 1) {
                throw new RuntimeException("Only one @Provides per type (attribute 'with') is allowed");
            }
            return listeners.iterator().next();
        } else {
            return null;
        }
    }
}

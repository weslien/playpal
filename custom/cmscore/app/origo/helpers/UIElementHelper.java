package origo.helpers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Helper methods to organize child UIElements in each UIElement.
 */
public class UIElementHelper {

    public static void reorderUIElements(List<play.modules.origo.core.ui.UIElement> elements) {
        Collections.sort(elements, new Comparator<play.modules.origo.core.ui.UIElement>() {
            @Override
            public int compare(play.modules.origo.core.ui.UIElement uiElement, play.modules.origo.core.ui.UIElement uiElement1) {
                return (uiElement.getWeight() >= uiElement1.getWeight()) ? 1 : 0;
            }
        });
    }

    public static void repositionUIElements(List<play.modules.origo.core.ui.UIElement> elements, play.modules.origo.core.ui.UIElement element) {
        for (play.modules.origo.core.ui.UIElement elem : elements) {
            if (elem.getWeight() >= element.getWeight()) {
                elem.setWeight(elem.getWeight() + 1);
            }
        }
    }


}

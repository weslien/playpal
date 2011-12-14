package helpers;

import play.modules.cmscore.ui.UIElement;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UIElementHelper {

    public static void reorderUIElements(List<UIElement> elements) {
        Collections.sort(elements, new Comparator<UIElement>() {
            @Override
            public int compare(UIElement uiElement, UIElement uiElement1) {
                return (uiElement.getWeight() >= uiElement1.getWeight()) ? 1 : 0;
            }
        });
    }

    public static void repositionUIElements(List<UIElement> elements, UIElement element) {
        for(UIElement elem : elements){
            if(elem.getWeight() >= element.getWeight()){
                elem.setWeight(elem.getWeight()+1);
            }
        }
    }


}

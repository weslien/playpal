package play.modules.cmscore.annotations;

/**
 * Used in conjunction with the \@Decorate annotation.
 * A method with the \@Decorate(type=FORM) will be called with a Form UIElement so the theme
 * can decorate it and return the HTML to be rendered.
 */
public enum UIElementType {

    HEAD, META, SCRIPT, TITLE, BODY,

    LIST_BULLET, LIST_ORDERED, LIST_ITEM,

    FORM, INPUT_HIDDEN, INPUT_TEXT, INPUT_TEXTAREA, INPUT_RADIO_BUTTON, INPUT_BUTTON, INPUT_SUBMIT, INPUT_RESET, INPUT_IMAGE, INPUT_FILE, INPUT_PASSWORD,

    PANEL, TEXT
    
}

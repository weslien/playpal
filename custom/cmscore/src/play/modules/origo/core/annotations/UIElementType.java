package play.modules.origo.core.annotations;

/**
 * Used in conjunction with the \@Decorates annotation.
 * A method with the \@Decorates(type=FORM) will be called with a Form UIElement so the theme
 * can decorate it and return the HTML to be rendered.
 */
public enum UIElementType {

    META, SCRIPT, STYLE, LINK,

    LIST_BULLET, LIST_ORDERED, LIST_ITEM,

    FORM, LABEL, INPUT_HIDDEN, INPUT_TEXT, INPUT_TEXTAREA, INPUT_RADIO_BUTTON, INPUT_SELECT, INPUT_SELECT_OPTION, INPUT_BUTTON, INPUT_SUBMIT, INPUT_RESET, INPUT_IMAGE, INPUT_FILE, INPUT_PASSWORD,

    PANEL, TEXT

}
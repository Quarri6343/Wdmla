package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.impl.ui.style.PanelStyle;

/**
 * Collections of pre-made settings that can override default or custom ui options.<br>
 * These can be chosen in mod options menu
 */
public final class Theme {

    public int bgColor;
    public int bgGradient1;
    public int bgGradient2;
    public TextColors textColors;
    public int breakProgress_default;
    public PanelStyle panelStyle;

    public Theme(int bgColor, int bgGradient1, int bgGradient2, TextColors textColors, PanelStyle panelStyle) {
        this.bgColor = bgColor;
        this.bgGradient1 = bgGradient1;
        this.bgGradient2 = bgGradient2;
        this.textColors = textColors;
        this.panelStyle = panelStyle;
    }

    public int textColor(MessageType type) {
        return switch (type) {
            case INFO -> textColors.info;
            case TITLE -> textColors.title;
            case SUCCESS -> textColors.success;
            case WARNING -> textColors.warning;
            case DANGER -> textColors.danger;
            case FAILURE -> textColors.failure;
            case MOD_NAME -> textColors.modName;
            default -> textColors._default;
        };
    }
}

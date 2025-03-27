package com.gtnewhorizons.wdmla.api;

import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.impl.ui.style.AmountStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.PanelStyle;

public final class Theme {

    public int bgColor;
    public int bgGradient1;
    public int bgGradient2;
    public TextColors textColors;
    public AmountStyle amountStyle;
    public int breakProgress_default;
    public int breakProgress_failure;
    public PanelStyle panelStyle;

    public Theme(int bgColor, int bgGradient1, int bgGradient2,
                 AmountStyle amountStyle, TextColors textColors,
                 int breakProgress_default, int breakProgress_failure, PanelStyle panelStyle) {
        this.bgColor = bgColor;
        this.bgGradient1 = bgGradient1;
        this.bgGradient2 = bgGradient2;
        this.textColors = textColors;
        this.amountStyle = amountStyle;
        this.breakProgress_default = breakProgress_default;
        this.breakProgress_failure = breakProgress_failure;
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

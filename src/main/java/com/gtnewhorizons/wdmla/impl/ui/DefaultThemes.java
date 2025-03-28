package com.gtnewhorizons.wdmla.impl.ui;

import com.gtnewhorizons.wdmla.api.TextColors;
import com.gtnewhorizons.wdmla.api.Theme;
import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.impl.ui.style.AmountStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.PanelStyle;

public enum DefaultThemes {
    CUSTOM(
            0,
            0,
            0,
            new TextColors(0, ColorPalette.INFO, ColorPalette.TITLE, ColorPalette.SUCCESS,
                    ColorPalette.WARNING, ColorPalette.DANGER, ColorPalette.FAILURE, ColorPalette.MOD_NAME),
            new PanelStyle(2, 2)),
    JADE(
            ColorPalette.BG_COLOR_JADE,
            ColorPalette.BG_GRADIENT1_JADE,
            ColorPalette.BG_GRADIENT2_JADE,
            new TextColors(ColorPalette.DEFAULT, ColorPalette.INFO, ColorPalette.TITLE, ColorPalette.SUCCESS_JADE,
                    ColorPalette.WARNING, ColorPalette.DANGER, ColorPalette.FAILURE, ColorPalette.MOD_NAME),
            new PanelStyle(1, 2)), //Jade has tight spacing than others
    TOP(
            ColorPalette.BG_COLOR_TOP,
            ColorPalette.BG_GRADIENT1_TOP,
            ColorPalette.BG_GRADIENT2_TOP,
            new TextColors(ColorPalette.DEFAULT, ColorPalette.INFO, ColorPalette.TITLE, ColorPalette.SUCCESS_TOP,
                    ColorPalette.WARNING_TOP, ColorPalette.DANGER, ColorPalette.FAILURE, ColorPalette.MOD_NAME),
            new PanelStyle(2, 2));

    private Theme theme;

    DefaultThemes(int bgColor, int gradient1, int gradient2, TextColors textColors, PanelStyle panelStyle){
        this.theme = new Theme(bgColor, gradient1, gradient2,
                textColors,
                panelStyle);
    }

    public Theme get() {
        return theme;
    }
}

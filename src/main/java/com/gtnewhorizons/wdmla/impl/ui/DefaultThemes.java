package com.gtnewhorizons.wdmla.impl.ui;

import com.gtnewhorizons.wdmla.api.TextColors;
import com.gtnewhorizons.wdmla.api.Theme;
import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.impl.ui.style.AmountStyle;
import mcp.mobius.waila.overlay.OverlayConfig;

public enum DefaultThemes {
    WAILA(
            OverlayConfig.bgcolor,
            OverlayConfig.gradient1,
            OverlayConfig.gradient2,
            new TextColors(OverlayConfig.fontcolor, ColorPalette.INFO, ColorPalette.TITLE, ColorPalette.SUCCESS,
                    ColorPalette.WARNING, ColorPalette.DANGER, ColorPalette.FAILURE, ColorPalette.MOD_NAME)),
    JADE(
            ColorPalette.BG_COLOR_JADE,
            ColorPalette.BG_GRADIENT1_JADE,
            ColorPalette.BG_GRADIENT2_JADE,
            new TextColors(ColorPalette.DEFAULT_JADE, ColorPalette.INFO, ColorPalette.TITLE, ColorPalette.SUCCESS_JADE,
                    ColorPalette.WARNING, ColorPalette.DANGER, ColorPalette.FAILURE, ColorPalette.MOD_NAME)),
    TOP(
            ColorPalette.BG_COLOR_TOP,
            ColorPalette.BG_GRADIENT1_TOP,
            ColorPalette.BG_GRADIENT2_TOP,
            new TextColors(ColorPalette.DEFAULT_TOP, ColorPalette.INFO, ColorPalette.TITLE, ColorPalette.SUCCESS_TOP,
                    ColorPalette.WARNING_TOP, ColorPalette.DANGER, ColorPalette.FAILURE, ColorPalette.MOD_NAME));

    private Theme theme;

    DefaultThemes(int bgColor, int gradient1, int gradient2, TextColors textColors){
        this.theme = new Theme(bgColor, gradient1, gradient2,
                new AmountStyle(ColorPalette.AMOUNT_BORDER_WAILA,
                        ColorPalette.AMOUNT_BACKGROUND_WAILA,
                        ColorPalette.AMOUNT_FILLED_WAILA,
                        ColorPalette.AMOUNT_FILLED_ALTERNATE_WAILA),
                textColors,
                ColorPalette.BREAK_PROGRESS_DEFAULT,
                ColorPalette.BREAK_PROGRESS_FAILURE);
    }

    public Theme get() {
        return theme;
    }
}

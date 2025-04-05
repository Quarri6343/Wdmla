package com.gtnewhorizons.wdmla.impl.ui.style;

import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.api.ui.style.IAmountStyle;

public class AmountStyle implements IAmountStyle {

    private int borderColor;
    private int backgroundColor;
    private int filledColor;
    private int alternateFilledColor;

    public AmountStyle(int borderColor, int backgroundColor, int filledColor, int alternateFilledColor) {
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        this.filledColor = filledColor;
        this.alternateFilledColor = alternateFilledColor;
    }

    public AmountStyle() {
        this.borderColor = ColorPalette.AMOUNT_BORDER_WAILA;
        this.backgroundColor = ColorPalette.AMOUNT_BACKGROUND_WAILA;
        this.filledColor = ColorPalette.AMOUNT_FILLED_WAILA;
        this.alternateFilledColor = ColorPalette.AMOUNT_FILLED_ALTERNATE_WAILA;
    }

    public AmountStyle borderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public AmountStyle backgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public AmountStyle filledColor(int filledColor) {
        this.filledColor = filledColor;
        return this;
    }

    public AmountStyle alternateFilledColor(int alternateFilledColor) {
        this.alternateFilledColor = alternateFilledColor;
        return this;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public int getBackgroundColor1() {
        return backgroundColor;
    }

    @Override
    public int getBackgroundColor2() {
        return backgroundColor;
    }

    @Override
    public int getFilledColor() {
        return filledColor;
    }

    @Override
    public int getAlternateFilledColor() {
        return alternateFilledColor;
    }
}

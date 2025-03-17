package com.gtnewhorizons.wdmla.impl.ui.style;

import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.api.ui.style.IAmountStyle;

public class AmountStyle implements IAmountStyle {

    private int borderColor;
    private int backgroundColor;
    private int filledColor;
    private int alternateFilledColor;

    public AmountStyle() {
        this.borderColor = ColorPalette.AMOUNT_DEFAULT_BORDER;
        this.backgroundColor = ColorPalette.AMOUNT_DEFAULT_BACKGROUND;
        this.filledColor = ColorPalette.AMOUNT_DEFAULT_FILLED;
        this.alternateFilledColor = ColorPalette.AMOUNT_DEFAULT_FILLED_ALTERNATE;
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
    public int getBackgroundColor() {
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

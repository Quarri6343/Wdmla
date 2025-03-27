package com.gtnewhorizons.wdmla.impl.ui.style;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.Theme;
import com.gtnewhorizons.wdmla.api.ui.style.IAmountStyle;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;

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
        IAmountStyle style = WDMlaConfig.instance().getEnum(Identifiers.CONFIG_CURRENT_THEME).get().amountStyle;
        this.borderColor = style.getBorderColor();
        this.backgroundColor = style.getBackgroundColor();
        this.filledColor = style.getFilledColor();
        this.alternateFilledColor = style.getAlternateFilledColor();
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

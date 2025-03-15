package com.gtnewhorizons.wdmla.impl.ui.style;

import com.gtnewhorizons.wdmla.api.ColorCodes;
import com.gtnewhorizons.wdmla.api.ui.ComponentAlignment;
import com.gtnewhorizons.wdmla.api.ui.style.ITextStyle;

public class TextStyle implements ITextStyle {

    private ComponentAlignment alignment;
    private int color;
    private boolean shadow;

    public TextStyle() {
        this.alignment = ComponentAlignment.TOPLEFT;
        this.color = ColorCodes.DEFAULT_TEXT;
        this.shadow = DEFAULT_SHADOW;
    }

    public TextStyle alignment(ComponentAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public TextStyle color(int color) {
        this.color = color;
        return this;
    }

    public TextStyle shadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override
    public ComponentAlignment getAlignment() {
        return alignment;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public boolean getShadow() {
        return shadow;
    }
}

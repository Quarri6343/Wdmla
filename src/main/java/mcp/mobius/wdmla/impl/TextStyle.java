package mcp.mobius.wdmla.impl;

import mcp.mobius.waila.api.elements.ElementAlignment;
import mcp.mobius.wdmla.api.ITextStyle;

public class TextStyle implements ITextStyle {

    private ElementAlignment alignment = ElementAlignment.ALIGN_TOPLEFT;
    private int color = 0xDEDEDE;
    private boolean shadow = true;

    @Override
    public ITextStyle alignment(ElementAlignment align) {
        this.alignment = align;
        return this;
    }

    @Override
    public ITextStyle color(int color) {
        this.color = color;
        return this;
    }

    @Override
    public ITextStyle shadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override
    public ElementAlignment getAlignment() {
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

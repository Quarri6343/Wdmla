package mcp.mobius.wdmla.impl;

import mcp.mobius.waila.api.elements.ElementAlignment;
import mcp.mobius.wdmla.api.IPanelStyle;

public class PanelStyle implements IPanelStyle {

    private int borderColor = NO_BORDER;
    private ElementAlignment alignment = ElementAlignment.ALIGN_TOPLEFT;
    private int spacing = DEFAULT_SPACE;

    @Override
    public IPanelStyle borderColor(int c) {
        borderColor = c;
        return this;
    }

    @Override
    public IPanelStyle alignment(ElementAlignment align) {
        alignment = align;
        return this;
    }

    @Override
    public IPanelStyle spacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public ElementAlignment getAlignment() {
        return alignment;
    }

    @Override
    public int getSpacing() {
        return spacing;
    }
}

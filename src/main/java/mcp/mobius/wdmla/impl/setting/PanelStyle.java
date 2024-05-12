package mcp.mobius.wdmla.impl.setting;

import mcp.mobius.waila.api.elements.ElementAlignment;
import mcp.mobius.wdmla.api.IPanelStyle;

public class PanelStyle implements IPanelStyle {

    private final int borderColor;
    private final ElementAlignment alignment;
    private final int spacing;

    public PanelStyle() {
        this.borderColor = NO_BORDER;
        this.alignment = ElementAlignment.ALIGN_TOPLEFT;
        this.spacing = DEFAULT_SPACE;
    }

    public PanelStyle(int borderColor, ElementAlignment alignment, int spacing) {
        this.borderColor = borderColor;
        this.alignment = alignment;
        this.spacing = spacing;
    }

    public PanelStyle borderColor(int borderColor) {
        return new PanelStyle(borderColor, alignment, spacing);
    }

    public PanelStyle alignment(ElementAlignment alignment) {
        return new PanelStyle(borderColor, alignment, spacing);
    }

    public PanelStyle spacing(int spacing) {
        return new PanelStyle(borderColor, alignment, spacing);
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

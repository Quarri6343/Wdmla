package mcp.mobius.wdmla.impl.setting;

import mcp.mobius.wdmla.api.IPanelStyle;
import mcp.mobius.wdmla.api.WidgetAlignment;

public class PanelStyle implements IPanelStyle {

    private final int borderColor;
    private final WidgetAlignment alignment;
    private final int spacing;

    public PanelStyle() {
        this.borderColor = NO_BORDER;
        this.alignment = WidgetAlignment.TOPLEFT;
        this.spacing = DEFAULT_SPACE;
    }

    public PanelStyle(int borderColor, WidgetAlignment alignment, int spacing) {
        this.borderColor = borderColor;
        this.alignment = alignment;
        this.spacing = spacing;
    }

    public PanelStyle borderColor(int borderColor) {
        return new PanelStyle(borderColor, alignment, spacing);
    }

    public PanelStyle alignment(WidgetAlignment alignment) {
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
    public WidgetAlignment getAlignment() {
        return alignment;
    }

    @Override
    public int getSpacing() {
        return spacing;
    }
}

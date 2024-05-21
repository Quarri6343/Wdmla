package mcp.mobius.wdmla.impl.ui.value.setting;

import mcp.mobius.wdmla.api.ui.ComponentAlignment;
import mcp.mobius.wdmla.api.ui.style.IPanelStyle;

public class PanelStyle implements IPanelStyle {

    private final int borderColor;
    private final ComponentAlignment alignment;
    private final int spacing;

    public PanelStyle() {
        this.borderColor = NO_BORDER;
        this.alignment = ComponentAlignment.TOPLEFT;
        this.spacing = DEFAULT_SPACE;
    }

    public PanelStyle(int borderColor, ComponentAlignment alignment, int spacing) {
        this.borderColor = borderColor;
        this.alignment = alignment;
        this.spacing = spacing;
    }

    public PanelStyle borderColor(int borderColor) {
        return new PanelStyle(borderColor, alignment, spacing);
    }

    public PanelStyle alignment(ComponentAlignment alignment) {
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
    public ComponentAlignment getAlignment() {
        return alignment;
    }

    @Override
    public int getSpacing() {
        return spacing;
    }
}

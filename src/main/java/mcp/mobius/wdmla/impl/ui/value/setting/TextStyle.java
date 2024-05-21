package mcp.mobius.wdmla.impl.ui.value.setting;

import mcp.mobius.wdmla.api.ui.ComponentAlignment;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;

public class TextStyle implements ITextStyle {

    private final ComponentAlignment alignment;
    private final int color;
    private final boolean shadow;

    public TextStyle() {
        this.alignment = ComponentAlignment.TOPLEFT;
        this.color = DEFAULT_COLOR;
        this.shadow = DEFAULT_SHADOW;
    }

    public TextStyle(ComponentAlignment alignment, int color, boolean shadow) {
        this.alignment = alignment;
        this.color = color;
        this.shadow = shadow;
    }

    public TextStyle alignment(ComponentAlignment alignment) {
        return new TextStyle(alignment, color, shadow);
    }

    public TextStyle color(int color) {
        return new TextStyle(alignment, color, shadow);
    }

    public TextStyle shadow(boolean shadow) {
        return new TextStyle(alignment, color, shadow);
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

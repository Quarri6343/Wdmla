package mcp.mobius.wdmla.impl.ui.value.setting;

import mcp.mobius.wdmla.api.ui.ComponentAlignment;
import mcp.mobius.wdmla.api.ui.style.ITextStyle;

public class TextStyle implements ITextStyle {

    private ComponentAlignment alignment;
    private int color;
    private boolean shadow;

    public TextStyle() {
        this.alignment = ComponentAlignment.TOPLEFT;
        this.color = DEFAULT_COLOR;
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

package mcp.mobius.wdmla.impl.setting;

import mcp.mobius.waila.api.elements.ElementAlignment;
import mcp.mobius.wdmla.api.ITextStyle;

public class TextStyle implements ITextStyle {

    private final ElementAlignment alignment;
    private final int color;
    private final boolean shadow;

    public TextStyle() {
        this.alignment = ElementAlignment.ALIGN_TOPLEFT;
        this.color = DEFAULT_COLOR;
        this.shadow = DEFAULT_SHADOW;
    }

    public TextStyle(ElementAlignment alignment, int color, boolean shadow) {
        this.alignment = alignment;
        this.color = color;
        this.shadow = shadow;
    }

    public TextStyle alignment(ElementAlignment alignment) {
        return new TextStyle(alignment, color, shadow);
    }

    public TextStyle color(int color) {
        return new TextStyle(alignment, color, shadow);
    }

    public TextStyle shadow(boolean shadow) {
        return new TextStyle(alignment, color, shadow);
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

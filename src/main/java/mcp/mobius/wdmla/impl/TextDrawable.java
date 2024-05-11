package mcp.mobius.wdmla.impl;

import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.ITextStyle;

public class TextDrawable implements IDrawable {

    private final String text;
    private ITextStyle style;

    public TextDrawable(String text) {
        this.text = text;
    }

    public TextDrawable style(ITextStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public void draw(int x, int y) {
        DisplayUtil.drawString(text, x, y, style.getColor(), style.getShadow());
    }
}

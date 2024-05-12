package mcp.mobius.wdmla.impl.drawable;

import mcp.mobius.wdmla.util.RenderUtil;
import mcp.mobius.wdmla.api.IArea;
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
    public void draw(IArea area) {
        RenderUtil.drawString(text, area.getX(), area.getY(), style.getColor(), style.getShadow());
    }
}

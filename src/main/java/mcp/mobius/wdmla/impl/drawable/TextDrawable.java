package mcp.mobius.wdmla.impl.drawable;

import mcp.mobius.wdmla.impl.setting.TextStyle;
import mcp.mobius.wdmla.util.RenderUtil;
import mcp.mobius.wdmla.api.IArea;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.ITextStyle;

public class TextDrawable implements IDrawable {

    private final String text;
    private final ITextStyle style;

    public TextDrawable(String text) {
        this.text = text;
        this.style = new TextStyle();
    }

    private TextDrawable(String text, ITextStyle style) {
        this.text = text;
        this.style = style;
    }

    public TextDrawable style(ITextStyle style) {
        return new TextDrawable(text, style);
    }

    @Override
    public void draw(IArea area) {
        RenderUtil.drawString(text, area.getX(), area.getY(), style.getColor(), style.getShadow());
    }
}

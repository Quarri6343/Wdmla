package mcp.mobius.wdmla.impl.drawable;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.sizer.IArea;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.ITextStyle;
import mcp.mobius.wdmla.impl.values.setting.TextStyle;

public class TextDrawable implements IDrawable {

    private final @NotNull String text;
    private final @NotNull ITextStyle style;

    public TextDrawable(@NotNull String text) {
        this.text = text;
        this.style = new TextStyle();
    }

    private TextDrawable(@NotNull String text, @NotNull ITextStyle style) {
        this.text = text;
        this.style = style;
    }

    public TextDrawable style(ITextStyle style) {
        return new TextDrawable(text, style);
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.drawString(text, area.getX(), area.getY(), style.getColor(), style.getShadow());
    }
}

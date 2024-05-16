package mcp.mobius.wdmla.impl.drawable;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.sizer.IArea;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.IPanelStyle;
import mcp.mobius.wdmla.impl.values.setting.PanelStyle;

public class BorderDrawable implements IDrawable {

    protected final @NotNull IPanelStyle style;

    public BorderDrawable() {
        style = new PanelStyle();
    }

    private BorderDrawable(@NotNull IPanelStyle style) {
        this.style = style;
    }

    public BorderDrawable style(IPanelStyle style) {
        return new BorderDrawable(style);
    }

    @Override
    public void draw(IArea area) {
        int color = this.style.getBorderColor();
        if (this.style.getBorderColor() != IPanelStyle.NO_BORDER) {
            GuiDraw.drawHorizontalLine(area.getX(), area.getY(), area.getEX() - 1, color);
            GuiDraw.drawHorizontalLine(area.getX(), area.getEY() - 1, area.getEX() - 1, color);
            GuiDraw.drawVerticalLine(area.getX(), area.getY(), area.getEY() - 1, color);
            GuiDraw.drawVerticalLine(area.getEX() - 1, area.getY(), area.getEY(), color);
        }
    }
}

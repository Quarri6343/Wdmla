package mcp.mobius.wdmla.impl;

import net.minecraft.client.gui.Gui;

import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.IPanelStyle;
import mcp.mobius.wdmla.api.ISize;

public class BorderDrawable implements IDrawable {

    protected ISize size;
    protected IPanelStyle style;

    public BorderDrawable size(ISize size) {
        this.size = size;
        return this;
    }

    public BorderDrawable style(IPanelStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public void draw(int x, int y) {
        if (this.style.getBorderColor() != IPanelStyle.NO_BORDER) {
            int w = this.size.getW();
            int h = this.size.getH();
            drawHorizontalLine(x, y, x + w - 1, this.style.getBorderColor());
            drawHorizontalLine(x, y + h - 1, x + w - 1, this.style.getBorderColor());
            drawVerticalLine(x, y, y + h - 1, this.style.getBorderColor());
            drawVerticalLine(x + w - 1, y, y + h, this.style.getBorderColor());
        }
    }

    public void drawHorizontalLine(int x1, int y1, int x2, int color) {
        Gui.drawRect(x1, y1, x2, y1 + 1, color);
    }

    public void drawVerticalLine(int x1, int y1, int y2, int color) {
        Gui.drawRect(x1, y1, x1 + 1, y2, color);
    }
}

package mcp.mobius.wdmla.impl.ui.drawable;

import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.wdmla.api.ui.IDrawable;
import mcp.mobius.wdmla.api.ui.sizer.IArea;
import mcp.mobius.wdmla.impl.ui.value.sizer.Area;

public class BackgroundDrawable implements IDrawable {

    @Override
    public void draw(IArea area) {
        int x = area.getX();
        int y = area.getY();
        int w = area.getW();
        int h = area.getH();
        int bg = OverlayConfig.bgcolor;
        int grad1 = OverlayConfig.gradient1;
        int grad2 = OverlayConfig.gradient2;

        GuiDraw.drawGradientRect(new Area(x + 1, y, w - 1, 1), bg, bg);
        GuiDraw.drawGradientRect(new Area(x + 1, y + h, w - 1, 1), bg, bg);

        GuiDraw.drawGradientRect(new Area(x + 1, y + 1, w - 1, h - 1), bg, bg);// center

        GuiDraw.drawGradientRect(new Area(x, y + 1, 1, h - 1), bg, bg);
        GuiDraw.drawGradientRect(new Area(x + w, y + 1, 1, h - 1), bg, bg);
        GuiDraw.drawGradientRect(new Area(x + 1, y + 2, 1, h - 3), grad1, grad2);
        GuiDraw.drawGradientRect(new Area(x + w - 1, y + 2, 1, h - 3), grad1, grad2);
        GuiDraw.drawGradientRect(new Area(x + 1, y + 1, w - 1, 1), grad1, grad1);
        GuiDraw.drawGradientRect(new Area(x + 1, y + h - 1, w - 1, 1), grad2, grad2);
    }
}

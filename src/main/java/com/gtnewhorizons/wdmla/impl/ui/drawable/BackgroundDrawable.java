package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.Theme;
import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.config.General;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Area;

public class BackgroundDrawable implements IDrawable {

    private final Theme theme;

    public BackgroundDrawable() {
        this.theme = General.currentTheme.get();
    }

    @Override
    public void draw(IArea area) {
        int x = area.getX();
        int y = area.getY();
        int w = area.getW();
        int h = area.getH();
        int bg = theme.bgColor;
        int grad1 = theme.bgGradient1;
        int grad2 = theme.bgGradient2;

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

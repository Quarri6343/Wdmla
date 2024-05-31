package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.overlay.VanillaIconUI;

public class VanillaIconDrawable implements IDrawable {

    private final VanillaIconUI icon;

    public VanillaIconDrawable(VanillaIconUI icon) {
        this.icon = icon;
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.renderVanillaIcon(area.getX(), area.getY(), area.getW(), area.getH(), icon);
    }
}

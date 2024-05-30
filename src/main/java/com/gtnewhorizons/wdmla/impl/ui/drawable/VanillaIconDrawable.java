package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.waila.overlay.VanillaIconUI;

import java.awt.*;

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

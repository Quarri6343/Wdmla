package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.overlay.GuiDraw;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class IconDrawable implements IDrawable {

    private final IIcon icon;
    private final ResourceLocation path;

    public IconDrawable(IIcon icon, ResourceLocation path) {
        this.icon = icon;
        this.path = path;
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.renderVanillaIcon(area.getX(), area.getY(), area.getW(), area.getH(), icon, path);
    }
}

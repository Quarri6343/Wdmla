package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.overlay.GuiDraw;
import net.minecraftforge.fluids.FluidStack;

public class FluidDrawable implements IDrawable {

    private final FluidStack fluidStack;

    public FluidDrawable(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.renderFluidStack(fluidStack, area.getX(), area.getY(), area.getW(), area.getH(), 0);
    }
}

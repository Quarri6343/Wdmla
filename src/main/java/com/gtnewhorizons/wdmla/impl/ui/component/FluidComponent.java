package com.gtnewhorizons.wdmla.impl.ui.component;

import net.minecraftforge.fluids.FluidStack;

import com.gtnewhorizons.wdmla.impl.ui.drawable.FluidDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;

// tmp
public class FluidComponent extends Component {

    public static final int DEFAULT_W = 16;
    public static final int DEFAULT_H = 16;

    public FluidComponent(FluidStack fluidStack) {
        super(new Padding(), new Size(DEFAULT_W, DEFAULT_H), new FluidDrawable(fluidStack));
    }
}

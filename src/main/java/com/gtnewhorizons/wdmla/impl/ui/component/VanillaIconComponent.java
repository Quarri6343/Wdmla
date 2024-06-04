package com.gtnewhorizons.wdmla.impl.ui.component;

import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.impl.ui.drawable.VanillaIconDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.overlay.VanillaIconUI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

// TODO: support any icon
public class VanillaIconComponent extends TooltipComponent {

    public VanillaIconComponent(VanillaIconUI icon) {
        super(
                new ArrayList<>(),
                new Padding(),
                new Size(VanillaIconUI.SIZE, VanillaIconUI.SIZE),
                new VanillaIconDrawable(icon));
    }

    @Override
    public TooltipComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("You can't set the size of this component!");
    }
}

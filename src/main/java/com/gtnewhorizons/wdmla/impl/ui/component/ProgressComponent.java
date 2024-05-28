package com.gtnewhorizons.wdmla.impl.ui.component;

import java.util.ArrayList;

import com.gtnewhorizons.wdmla.impl.ui.drawable.ProgressDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.api.ui.style.IProgressStyle;
import com.gtnewhorizons.wdmla.impl.ui.value.Progress;

public class ProgressComponent extends TooltipComponent {

    public static final int DEFAULT_W = 100;
    public static final int DEFAULT_H = 12;

    public ProgressComponent(long current, long max) {
        super(
                new ArrayList<>(),
                new Padding(),
                new Size(DEFAULT_W, DEFAULT_H),
                new ProgressDrawable(new Progress(current, max)));
    }

    public ProgressComponent style(IProgressStyle style) {
        ((ProgressDrawable) foreground).style(style);
        return this;
    }
}

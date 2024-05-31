package com.gtnewhorizons.wdmla.impl.ui.component;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.impl.ui.drawable.HealthDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;

public class HealthComponent extends TooltipComponent {

    public HealthComponent(String[] args) {
        super(new ArrayList<>(), new Padding(), new Size(0, 0), new HealthDrawable(args));
        super.size(getSize(args));
    }

    public Size getSize(String[] args) {
        float maxHeartsPerLine = Float.parseFloat(args[0]);
        float maxHealth = Float.parseFloat(args[2]);

        int heartsPerLine = (int) (Math.min(maxHeartsPerLine, Math.ceil(maxHealth)));
        int nLines = (int) (Math.ceil(maxHealth / maxHeartsPerLine));

        return new Size(8 * heartsPerLine, 10 * nLines - 2);
    }

    @Override
    public TextComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("You can't set the size of this component!");
    }
}

package com.gtnewhorizons.wdmla.impl.ui.component;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.impl.ui.drawable.HealthDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;

public class HealthComponent extends TooltipComponent {

    private static final int DEFAULT_MAX_HEARTS_PER_LINE = 10;

    public HealthComponent(float health, float maxHealth) {
        this(DEFAULT_MAX_HEARTS_PER_LINE, health, maxHealth);
    }

    public HealthComponent(int maxHeartsPerLine, float health, float maxHealth) {
        super(
                new ArrayList<>(),
                new Padding(),
                new Size(0, 0),
                new HealthDrawable(maxHeartsPerLine, health, maxHealth));
        super.size(getSize(maxHeartsPerLine, maxHealth));
    }

    public Size getSize(int maxHeartsPerLine, float maxHealth) {

        int heartsPerLine = (int) (Math.min(maxHeartsPerLine, Math.ceil(maxHealth)));
        int nLines = (int) (Math.ceil(maxHealth / maxHeartsPerLine));

        return new Size(8 * heartsPerLine, 10 * nLines - 2);
    }

    @Override
    public TooltipComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("You can't set the size of this component!");
    }
}

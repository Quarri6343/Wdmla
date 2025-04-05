package com.gtnewhorizons.wdmla.impl.ui.component;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.sizer.ISize;
import com.gtnewhorizons.wdmla.impl.ui.drawable.ArmorDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;

public class ArmorComponent extends TooltipComponent {

    private static final int DEFAULT_MAX_ARMORS_PER_LINE = 10;

    public ArmorComponent(float armor) {
        this(DEFAULT_MAX_ARMORS_PER_LINE, armor, armor);
    }

    public ArmorComponent(float armor, float maxArmor) {
        this(DEFAULT_MAX_ARMORS_PER_LINE, armor, maxArmor);
    }

    public ArmorComponent(int maxArmorsPerLine, float armor, float maxArmor) {
        super(new ArrayList<>(), new Padding(), new Size(0, 0), new ArmorDrawable(maxArmorsPerLine, armor, maxArmor));
        super.size(getSize(maxArmorsPerLine, maxArmor));
    }

    public Size getSize(int maxArmorsPerLine, float maxArmor) {

        int armorsPerLine = (int) (Math.min(maxArmorsPerLine, Math.ceil(maxArmor)));
        int nLines = (int) (Math.ceil(maxArmor / maxArmorsPerLine));

        return new Size(8 * armorsPerLine, 10 * nLines - 2);
    }

    @Override
    public TooltipComponent size(@NotNull ISize size) {
        throw new IllegalArgumentException("You can't set the size of this component!");
    }
}

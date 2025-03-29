package com.gtnewhorizons.wdmla.impl.ui.component;

import com.gtnewhorizons.wdmla.impl.ui.drawable.EntityDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import net.minecraft.entity.EntityLiving;

import java.util.ArrayList;

public class EntityComponent extends TooltipComponent {

    public static final int DEFAULT_W = 16;
    public static final int DEFAULT_H = 16;

    public EntityComponent(EntityLiving entity) {
        super(new ArrayList<>(), new Padding(), new Size(DEFAULT_W, DEFAULT_H), new EntityDrawable(entity));
    }
}

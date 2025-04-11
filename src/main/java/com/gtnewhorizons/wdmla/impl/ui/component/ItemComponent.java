package com.gtnewhorizons.wdmla.impl.ui.component;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.gtnewhorizons.wdmla.impl.ui.drawable.ItemDrawable;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;

public class ItemComponent extends TooltipComponent {

    public static final int DEFAULT_W = 16;
    public static final int DEFAULT_H = 16;

    public ItemComponent(ItemStack itemStack) {
        super(new ArrayList<>(),
                // internal offset fix
                new Padding(-1, 0, 0, 0), new Size(DEFAULT_W, DEFAULT_H), new ItemDrawable(itemStack));
    }

    public ItemComponent doDrawOverlay(boolean flag) {
        ((ItemDrawable) this.foreground).doDrawOverlay(flag);
        return this;
    }

    public ItemComponent stackSizeOverride(String text) {
        ((ItemDrawable) this.foreground).stackSizeOverride(text);
        return this;
    }
}

package com.gtnewhorizons.wdmla.impl.ui.drawable;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;

public class ItemDrawable implements IDrawable {

    private final @NotNull ItemStack item;

    public ItemDrawable(@NotNull ItemStack item) {
        this.item = item;
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.renderStack(area, item);
    }
}

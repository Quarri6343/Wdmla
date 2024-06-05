package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.api.ui.style.IItemStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.ItemStyle;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemDrawable implements IDrawable {

    private final @NotNull ItemStack item;
    private @NotNull IItemStyle style;

    public ItemDrawable(@NotNull ItemStack item) {
        this.item = item;
        this.style = new ItemStyle();
    }

    public ItemDrawable style(IItemStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public void draw(IArea area) {
        GuiDraw.renderStack(area, item, style.getDrawOverlay());
    }
}

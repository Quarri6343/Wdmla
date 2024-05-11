package mcp.mobius.wdmla.impl;

import net.minecraft.item.ItemStack;

import mcp.mobius.wdmla.api.ISize;

public class ItemWidget extends Widget {

    public ItemWidget(ItemStack itemStack) {
        super(new ItemDrawable(itemStack));
        size(new Size().w(16).h(16));
    }

    @Override
    public ItemWidget size(ISize size) {
        super.size(size);
        ((ItemDrawable) foreGround).size(size);

        return this;
    }
}

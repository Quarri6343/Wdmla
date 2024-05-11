package mcp.mobius.wdmla.impl;

import net.minecraft.item.ItemStack;

import mcp.mobius.waila.api.impl.elements.ItemStyle;
import mcp.mobius.waila.overlay.DisplayUtil;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.ISize;

public class ItemDrawable implements IDrawable {

    private final ItemStack item;
    private ISize size;

    public ItemDrawable(ItemStack item) {
        this.item = item;
    }

    public ItemDrawable size(ISize size) {
        this.size = size;
        return this;
    }

    @Override
    public void draw(int x, int y) {
        DisplayUtil.elementRenderStack(x, y, item, new ItemStyle(size.getW(), size.getH()));
    }
}

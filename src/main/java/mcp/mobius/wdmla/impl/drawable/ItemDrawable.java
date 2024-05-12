package mcp.mobius.wdmla.impl.drawable;

import mcp.mobius.wdmla.util.RenderUtil;
import net.minecraft.item.ItemStack;

import mcp.mobius.waila.api.impl.elements.ItemStyle;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.api.IArea;

public class ItemDrawable implements IDrawable {

    private final ItemStack item;

    public ItemDrawable(ItemStack item) {
        this.item = item;
    }

    @Override
    public void draw(IArea area) {
        RenderUtil.renderStack(area.getX(), area.getY(), item, area.getW(), area.getH());
    }
}

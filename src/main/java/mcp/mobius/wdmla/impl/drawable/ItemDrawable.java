package mcp.mobius.wdmla.impl.drawable;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import mcp.mobius.wdmla.api.IArea;
import mcp.mobius.wdmla.api.IDrawable;
import mcp.mobius.wdmla.util.RenderUtil;

public class ItemDrawable implements IDrawable {

    private final @NotNull ItemStack item;

    public ItemDrawable(@NotNull ItemStack item) {
        this.item = item;
    }

    @Override
    public void draw(IArea area) {
        RenderUtil.renderStack(area.getX(), area.getY(), item, area.getW(), area.getH());
    }
}

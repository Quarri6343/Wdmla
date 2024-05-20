package mcp.mobius.wdmla.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface IBlockAccessor extends Accessor {
    Block getBlock();

    TileEntity getTileEntity();

    ItemStack getItemForm();
}

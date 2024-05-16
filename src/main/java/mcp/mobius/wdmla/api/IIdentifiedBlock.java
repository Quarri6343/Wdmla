package mcp.mobius.wdmla.api;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface IIdentifiedBlock {
    Block getBlock();

    TileEntity getTileEntity();

    ItemStack getItemForm();

    World getWorld();

    EntityPlayer getPlayer();
}

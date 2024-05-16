package mcp.mobius.wdmla.impl.value;

import mcp.mobius.wdmla.api.IIdentifiedBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IdentifiedBlock implements IIdentifiedBlock {

    private final Block block;
    private final TileEntity tileEntity;
    private final ItemStack identifiedStack;

    private final World world;
    private final EntityPlayer player;

    public IdentifiedBlock(Block block, TileEntity tileEntity, ItemStack identifiedStack, World world, EntityPlayer player) {
        this.block = block;
        this.tileEntity = tileEntity;
        this.identifiedStack = identifiedStack;
        this.world = world;
        this.player = player;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public TileEntity getTileEntity() {
        return tileEntity;
    }

    @Override
    public ItemStack getItemForm() {
        return identifiedStack;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public EntityPlayer getPlayer() {
        return player;
    }
}

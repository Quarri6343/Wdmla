package mcp.mobius.wdmla.impl.value;

import mcp.mobius.wdmla.impl.ObjectDataCenter;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import mcp.mobius.wdmla.api.IBlockAccessor;

public class BlockAccessor implements IBlockAccessor {

    private final Block block;
    private final @Nullable TileEntity tileEntity;
    private final ItemStack identifiedStack;

    private final World world;
    private final EntityPlayer player;
    private final MovingObjectPosition mop;
    private final NBTTagCompound serverData;

    public BlockAccessor(Block block, @Nullable TileEntity tileEntity, ItemStack identifiedStack, World world,
                         EntityPlayer player, MovingObjectPosition mop) {
        this.block = block;
        this.tileEntity = tileEntity;
        this.identifiedStack = identifiedStack;
        this.world = world;
        this.player = player;
        this.mop = mop;
        this.serverData = ObjectDataCenter.getServerData();
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

    @Override
    public NBTTagCompound getServerData() {
        return serverData;
    }

    @Override
    public MovingObjectPosition getHitResult() {
        return mop;
    }

    public boolean isSameBlock(@Nullable IBlockAccessor accessor) {
        return accessor != null && accessor.getBlock().equals(this.getBlock());
    }
}

package com.gtnewhorizons.wdmla.api.accessor;

import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/**
 * Provides essential current block and tile entity information to
 * {@link com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider} for easy access<br>
 * without accessing internal engine.
 */
public interface BlockAccessor extends Accessor {

    Block getBlock();

    /**
     * @return tile entity on block pos, if exists.
     */
    @Nullable
    TileEntity getTileEntity();

    int getMetadata();

    /**
     * Be aware, this behaves different from {@link mcp.mobius.waila.api.IWailaDataProvider}'s itemstack argument, which
     * can be swapped with getWailaStack by any provider!
     * 
     * @return an auto fetched itemstack form of the block
     */
    ItemStack getItemForm();

    @Override
    default Class<? extends Accessor> getAccessorType() {
        return BlockAccessor.class;
    }

    /**
     * Builds Accessor instance in child class.
     */
    @ApiStatus.NonExtendable
    interface Builder {

        Builder level(World level);

        Builder player(EntityPlayer player);

        Builder serverData(NBTTagCompound serverData);

        Builder serverConnected(boolean connected);

        Builder showDetails(boolean showDetails);

        Builder hit(MovingObjectPosition hit);

        Builder block(Block block);

        default Builder tileEntity(TileEntity tileEntity) {
            return tileEntity(() -> tileEntity);
        }

        Builder itemForm(ItemStack itemStack);

        Builder tileEntity(Supplier<TileEntity> tileEntity);

        Builder meta(int metaData);

        Builder requireVerification();

        BlockAccessor build();
    }
}

package mcp.mobius.wdmla.api;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

public interface BlockAccessor extends Accessor {

    Block getBlock();

    TileEntity getTileEntity();

    ItemStack getItemForm();


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

        Builder requireVerification();

        BlockAccessor build();
    }
}

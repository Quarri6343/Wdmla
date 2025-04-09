package mcp.mobius.waila.api;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class was used to provide block/TileEntity specific information to tooltip adder.<br>
 * <br>
 * <br>
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * An instance of this interface is passed to most of Waila Block/TileEntity callbacks.
 *
 * @deprecated Modern counterpart: {@link BlockAccessor}
 * @author ProfMobius
 *
 */
@Deprecated
@BackwardCompatibility
public interface IWailaDataAccessor {

    @NotNull
    World getWorld();

    @NotNull
    EntityPlayer getPlayer();

    @NotNull
    Block getBlock();

    int getBlockID();

    String getBlockQualifiedName();

    int getMetadata();

    @Nullable
    TileEntity getTileEntity();

    @NotNull
    MovingObjectPosition getPosition();

    @Nullable
    Vec3 getRenderingPosition();

    @Nullable
    NBTTagCompound getNBTData();

    int getNBTInteger(NBTTagCompound tag, String keyname);

    double getPartialFrame();

    ForgeDirection getSide();

    @Nullable
    ItemStack getStack();

    float getBlockBreakDamage();
}

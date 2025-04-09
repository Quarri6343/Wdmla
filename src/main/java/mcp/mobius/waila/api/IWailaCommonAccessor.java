package mcp.mobius.waila.api;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
 * This class was used to provide common information to tooltip adder.<br>
 * <br>
 * <br>
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game.
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * Common accessor for both Entity and Block/TileEntity.<br>
 * Available data depends on what it is called upon (ie : getEntity() will return null if looking at a block, etc).<br>
 *
 * @deprecated Modern version : {@link com.gtnewhorizons.wdmla.api.Accessor}
 */
@Deprecated
@BackwardCompatibility
@SuppressWarnings("unused")
public interface IWailaCommonAccessor {

    @NotNull
    World getWorld();

    @NotNull
    EntityPlayer getPlayer();

    @Nullable
    Block getBlock();

    int getBlockID();

    @Nullable
    String getBlockQualifiedName();

    int getMetadata();

    @Nullable
    TileEntity getTileEntity();

    @Nullable
    Entity getEntity();

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
}

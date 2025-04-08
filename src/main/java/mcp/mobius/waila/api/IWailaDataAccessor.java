package mcp.mobius.waila.api;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * This class was used to provide block/TileEntity specific information to tooltip adder<br>
 * <br>
 * <br>
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * An instance of this interface is passed to most of Waila Block/TileEntity callbacks.
 *
 * @deprecated Modern counterpart: {@link com.gtnewhorizons.wdmla.api.BlockAccessor}
 * @author ProfMobius
 *
 */
@Deprecated
@BackwardCompatibility
public interface IWailaDataAccessor {

    World getWorld();

    EntityPlayer getPlayer();

    Block getBlock();

    int getBlockID();

    String getBlockQualifiedName();

    int getMetadata();

    TileEntity getTileEntity();

    MovingObjectPosition getPosition();

    Vec3 getRenderingPosition();

    NBTTagCompound getNBTData();

    int getNBTInteger(NBTTagCompound tag, String keyname);

    double getPartialFrame();

    ForgeDirection getSide();

    ItemStack getStack();

    float getBlockBreakDamage();
}

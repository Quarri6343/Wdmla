package mcp.mobius.waila.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * This was the special accessor for Forge MultiParts mod <br>
 * Don't use.
 * Normal version: {@link IWailaCommonAccessor}
 * <br>
 * <br>
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * An instance of this interface is passed to most of Waila FMP callbacks.
 *
 * @deprecated api rework
 * @author ProfMobius
 *
 */
@BackwardCompatibility
@Deprecated
public interface IWailaFMPAccessor {

    World getWorld();

    EntityPlayer getPlayer();

    TileEntity getTileEntity();

    MovingObjectPosition getPosition();

    NBTTagCompound getNBTData();

    NBTTagCompound getFullNBTData();

    int getNBTInteger(NBTTagCompound tag, String keyname);

    double getPartialFrame();

    Vec3 getRenderingPosition();

    String getID();
}

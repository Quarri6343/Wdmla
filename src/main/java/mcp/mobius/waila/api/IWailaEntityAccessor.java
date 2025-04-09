package mcp.mobius.waila.api;

import com.gtnewhorizons.wdmla.api.accessor.EntityAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class was used to provide Entity specific information to tooltip adder.<br>
 * <br>
 * <br>
 * The Accessor is used to get some basic data out of the game without having to request direct access to the game
 * engine.<br>
 * It will also return things that are unmodified by the overriding systems (like getWailaStack).<br>
 * An instance of this interface is passed to most of Waila Entity callbacks.
 *
 * @deprecated Modern counterpart: {@link EntityAccessor}
 * @author ProfMobius
 *
 */
@Deprecated
@BackwardCompatibility
public interface IWailaEntityAccessor {

    @NotNull
    World getWorld();

    @NotNull
    EntityPlayer getPlayer();

    @NotNull
    Entity getEntity();

    @NotNull
    MovingObjectPosition getPosition();

    Vec3 getRenderingPosition();

    @Nullable
    NBTTagCompound getNBTData();

    int getNBTInteger(NBTTagCompound tag, String keyname);

    double getPartialFrame();
}

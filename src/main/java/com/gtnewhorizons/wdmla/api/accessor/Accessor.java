package com.gtnewhorizons.wdmla.api.accessor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides essential information to {@link com.gtnewhorizons.wdmla.api.provider.IWDMlaProvider} for easy access to game object<br>
 * without accessing internal engine.
 */
public interface Accessor {

    World getWorld();

    /**
     * @return the player who looks at target
     */
    EntityPlayer getPlayer();

    /**
     * @return the NBT data written in {@link com.gtnewhorizons.wdmla.api.provider.IServerDataProvider#appendServerData(NBTTagCompound, Accessor)}
     */
    @NotNull
    NBTTagCompound getServerData();

    /**
     * @return the result of ray cast of player pointing at
     */
    MovingObjectPosition getHitResult();

    /**
     * @return is the server verified with {@link mcp.mobius.waila.network.Message0x00ServerPing} or not
     */
    boolean isServerConnected();

    /**
     * Change from Waila: "Show Details" is no longer bound to player sneaking.
     * All provider requires details to be shown must check this condition.
     * @return is the player pressing "Show Details" key
     */
    boolean showDetails();

    /**
     * contains accessor specific target in Object format<br>
     * It is meant to be called from abstract class instance.<br>
     * @return tile entity or regular entity or whatever
     */
    @Nullable
    Object getTarget();

    /**
     * gets the accessor child class type from abstract class<br>
     * @return accessor type
     */
    Class<? extends Accessor> getAccessorType();

    /**
     * check is the server data is suitable for this accessor
     * @param data server data want to verify
     * @return the data is suitable or not
     */
    boolean verifyData(NBTTagCompound data);
}

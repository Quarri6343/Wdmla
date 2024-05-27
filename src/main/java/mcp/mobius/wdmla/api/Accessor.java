package mcp.mobius.wdmla.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public interface Accessor {

    World getWorld();

    EntityPlayer getPlayer();

    NBTTagCompound getServerData();

    MovingObjectPosition getHitResult();

    boolean isServerConnected();

    boolean showDetails();

    Class<? extends Accessor> getAccessorType();

    boolean verifyData(NBTTagCompound data);
}

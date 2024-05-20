package mcp.mobius.wdmla.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface Accessor {

    World getWorld();

    EntityPlayer getPlayer();
}

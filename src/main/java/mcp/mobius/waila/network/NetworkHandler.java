package mcp.mobius.waila.network;

import mcp.mobius.waila.api.BackwardCompatibility;
import net.minecraft.entity.player.EntityPlayerMP;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import mcp.mobius.waila.Waila;

/**
 * Waila splits network event to this class.
 */
@Deprecated
@BackwardCompatibility
public class NetworkHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        Waila.log.info(String.format("Player %s connected. Sending ping", event.player));
        WailaPacketHandler.INSTANCE.sendTo(new Message0x00ServerPing(), (EntityPlayerMP) event.player);
    }
}

package mcp.mobius.waila.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.gtnewhorizons.wdmla.api.Mods;
import com.gtnewhorizons.wdmla.impl.ui.drawable.BreakProgressDrawable;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.handlers.VanillaTooltipHandler;
import mcp.mobius.waila.handlers.nei.NEIHandler;
import mcp.mobius.waila.server.ProxyServer;

/**
 * Waila client proxy.
 * 
 * @see com.gtnewhorizons.wdmla.ClientProxy
 */
public class ProxyClient extends ProxyServer {

    public ProxyClient() {}

    @Override
    public void registerHandlers() {

        if (Mods.NOTENOUGHITEMS.isLoaded()) {
            NEIHandler.register();
        } else {
            MinecraftForge.EVENT_BUS.register(new VanillaTooltipHandler());
        }

        ModuleRegistrar.instance().addConfig("General", "general.showents");
        ModuleRegistrar.instance().addConfig("General", "general.showhp");
        ModuleRegistrar.instance().addConfig("General", "general.showcrop");

        MinecraftForge.EVENT_BUS.register(new WorldUnloadEventHandler());
        MinecraftForge.EVENT_BUS.register(new BlockBreakEventHandler());
    }

    @Override
    public Object getFont() {
        return null;
    }

    public static class WorldUnloadEventHandler {

        @SubscribeEvent
        public void onWorldUnload(WorldEvent.Unload event) {
            DataAccessorCommon.instance = new DataAccessorCommon();
        }
    }

    public static class BlockBreakEventHandler {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public void onBlockBreak(BlockEvent.BreakEvent event) {
            if (event.getPlayer().equals(Minecraft.getMinecraft().thePlayer)
                    && event.block.getBlockHardness(event.world, event.x, event.y, event.z) != 0.0f) {
                BreakProgressDrawable.INSTANCE.isBlockBrokenRecently = true;
            }
        }
    }
}

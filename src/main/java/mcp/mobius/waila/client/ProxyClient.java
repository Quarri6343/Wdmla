package mcp.mobius.waila.client;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

import com.gtnewhorizons.wdmla.api.Mods;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import mcp.mobius.waila.gui.truetyper.FontLoader;
import mcp.mobius.waila.gui.truetyper.TrueTypeFont;
import mcp.mobius.waila.handlers.VanillaTooltipHandler;
import mcp.mobius.waila.handlers.nei.NEIHandler;
import mcp.mobius.waila.server.ProxyServer;

public class ProxyClient extends ProxyServer {

    TrueTypeFont minecraftiaFont;

    public ProxyClient() {}

    @Override
    public void registerHandlers() {

        LangUtil.loadLangDir("waila");

        if (Mods.NOTENOUGHITEMS.isLoaded()) {
            NEIHandler.register();
        } else {
            MinecraftForge.EVENT_BUS.register(new VanillaTooltipHandler());
        }

        ModuleRegistrar.instance().addConfig("General", "general.showents");
        ModuleRegistrar.instance().addConfig("General", "general.showhp");
        ModuleRegistrar.instance().addConfig("General", "general.showcrop");

        MinecraftForge.EVENT_BUS.register(new WorldUnloadEventHandler());
    }

    @Override
    public Object getFont() {
        if (minecraftiaFont == null)
            minecraftiaFont = FontLoader.createFont(new ResourceLocation("waila", "fonts/Minecraftia.ttf"), 14, true);
        return this.minecraftiaFont;
    }

    public static class WorldUnloadEventHandler {

        @SubscribeEvent
        public void onWorldUnload(WorldEvent.Unload event) {
            DataAccessorCommon.instance = new DataAccessorCommon();
        }
    }
}

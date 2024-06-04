package com.gtnewhorizons.wdmla;

import java.util.HashSet;

import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.test.TestMode;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import mcp.mobius.waila.Tags;

@Mod(
        modid = WDMla.MODID,
        name = "WDMla",
        version = Tags.GRADLETOKEN_VERSION,
        dependencies = "after:Waila",
        acceptableRemoteVersions = "*")
public class WDMla {

    public static final String MODID = "Wdmla";
    public static boolean FROZEN;

    @SidedProxy(clientSide = "com.gtnewhorizons.wdmla.ClientProxy", serverSide = "com.gtnewhorizons.wdmla.CommonProxy")
    public static CommonProxy proxy;

    /**
     * Edit this on dev env to enable test mode
     */
    public static TestMode testMode = TestMode.WDMla;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    public static void loadComplete() {
        if (FROZEN) {
            return;
        }
        FROZEN = true;

        // TODO: put test plugin here

        // TODO: sort with config
        WDMlaCommonRegistration.instance().priorities.sort(new HashSet<>());
        WDMlaCommonRegistration.instance().loadComplete();
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            WDMlaClientRegistration.instance().loadComplete();
        }
    }
}

package com.gtnewhorizons.wdmla;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import com.gtnewhorizons.wdmla.config.General;
import net.minecraft.launchwrapper.Launch;

import com.gtnewhorizons.wdmla.test.TestMode;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import mcp.mobius.waila.Tags;

@Mod(
        modid = WDMla.MODID,
        name = "WDMla",
        version = Tags.GRADLETOKEN_VERSION,
        dependencies = "after:Waila",
        acceptableRemoteVersions = "*",
        guiFactory = "com.gtnewhorizons.wdmla.config.GuiFactory")
public class WDMla {

    static {
        try {
            ConfigurationManager.registerConfig(General.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String MODID = "wdmla";

    @SidedProxy(clientSide = "com.gtnewhorizons.wdmla.ClientProxy", serverSide = "com.gtnewhorizons.wdmla.CommonProxy")
    public static CommonProxy proxy;

    /**
     * Edit this on dev env to switch test mode
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

    @Mod.EventHandler
    public void processIMC(FMLInterModComms.IMCEvent event) {
        proxy.processIMC(event);
    }

    public static boolean isDevEnv() {
        return (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }
}

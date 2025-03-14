package com.gtnewhorizons.wdmla;

import java.io.File;

import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyIguanaTweaks;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyTinkersConstruct;
import com.gtnewhorizons.wdmla.api.Mods;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
import com.gtnewhorizons.wdmla.overlay.WDMlaTickHandler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mcp.mobius.waila.api.impl.ConfigHandler;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        File wdmlaConfig = new File(event.getModConfigurationDirectory().getPath(), "WDMla.cfg");
        new WDMlaConfig(wdmlaConfig);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        FMLCommonHandler.instance().bus().register(new WDMlaTickHandler());
        FMLCommonHandler.instance().bus().register(this);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        if (Mods.TCONSTUCT.isLoaded()) {
            ProxyTinkersConstruct.init();
        }
        if (Mods.IGUANATWEAKS.isLoaded()) {
            ProxyIguanaTweaks.init();
        }
        if (Mods.GREGTECH.isLoaded()) {
            ProxyGregTech.init();
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(WDMla.MODID)) {
            WDMlaConfig.instance().save();
            WDMlaConfig.instance().reloadConfig();
            loadComplete(); // sort priorities

            ConfigHandler.instance().config.save();
        }
    }
}

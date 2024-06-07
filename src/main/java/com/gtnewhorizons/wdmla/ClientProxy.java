package com.gtnewhorizons.wdmla;

import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyIguanaTweaks;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyTinkersConstruct;
import com.gtnewhorizons.wdmla.api.Mods;
import com.gtnewhorizons.wdmla.overlay.WDMlaTickHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        FMLCommonHandler.instance().bus().register(new WDMlaTickHandler());
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
}

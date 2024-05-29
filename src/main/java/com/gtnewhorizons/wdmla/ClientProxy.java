package com.gtnewhorizons.wdmla;

import com.gtnewhorizons.wdmla.overlay.WdmlaTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        FMLCommonHandler.instance().bus().register(new WdmlaTickHandler());
    }
}

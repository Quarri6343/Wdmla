package com.gtnewhorizons.wdmla;

import com.gtnewhorizons.wdmla.api.IWdmlaPlugin;
import com.gtnewhorizons.wdmla.impl.WdmlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WdmlaCommonRegistration;
import com.gtnewhorizons.wdmla.test.TestPlugin;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {
        registerPlugins();
    }

    public void serverStarting(FMLServerStartingEvent event) {}

    public void registerPlugins() {
        IWdmlaPlugin testPlugin = new TestPlugin();
        testPlugin.register(WdmlaCommonRegistration.instance());
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            testPlugin.registerClient(WdmlaClientRegistration.instance());
        }
    }
}

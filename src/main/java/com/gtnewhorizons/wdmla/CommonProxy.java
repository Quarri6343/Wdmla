package com.gtnewhorizons.wdmla;

import com.gtnewhorizons.wdmla.addon.CorePlugin;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.impl.WDMlaClientRegistration;
import com.gtnewhorizons.wdmla.impl.WDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.test.TestMode;
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
        if (WDMla.testMode == TestMode.WDMla) {
            IWDMlaPlugin testPlugin = new TestPlugin();
            testPlugin.register(WDMlaCommonRegistration.instance());
            if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                testPlugin.registerClient(WDMlaClientRegistration.instance());
            }
        }

        IWDMlaPlugin corePlugin = new CorePlugin();
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            corePlugin.registerClient(WDMlaClientRegistration.instance());
        }
    }
}

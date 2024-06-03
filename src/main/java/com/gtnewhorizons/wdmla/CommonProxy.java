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
        WDMlaCommonRegistration common = WDMlaCommonRegistration.instance();
        common.startSession();
        //TODO: grab plugins via IMC
        registerBuiltInServerPlugins(common);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            WDMlaClientRegistration client = WDMlaClientRegistration.instance();
            client.startSession();
            registerBuiltInClientPlugins(client);
            client.endSession();
        }
        common.endSession();
        WDMla.loadComplete();
    }

    public void serverStarting(FMLServerStartingEvent event) {}

    public void registerBuiltInServerPlugins(WDMlaCommonRegistration commonRegistration) {
        if (WDMla.testMode == TestMode.WDMla) {
            new TestPlugin().register(commonRegistration);
        }
    }

    public void registerBuiltInClientPlugins(WDMlaClientRegistration clientRegistration) {
        new CorePlugin().registerClient(clientRegistration);

        if (WDMla.testMode == TestMode.WDMla) {
            new TestPlugin().registerClient(clientRegistration);
        }
    }
}

package com.gtnewhorizons.wdmla;

import com.gtnewhorizons.wdmla.addon.CorePlugin;
import com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityPlugin;
import com.gtnewhorizons.wdmla.addon.harvestability.MissingHarvestInfo;
import com.gtnewhorizons.wdmla.config.WDMlaConfig;
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

import java.io.File;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {}

    public void init(FMLInitializationEvent event) {
        MissingHarvestInfo.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        WDMlaCommonRegistration common = WDMlaCommonRegistration.instance();
        common.startSession();
        // TODO: grab external plugins via IMC
        registerBuiltInServerPlugins(common);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            WDMlaClientRegistration client = WDMlaClientRegistration.instance();
            client.startSession();
            registerBuiltInClientPlugins(client);
            client.endSession();

            WDMlaConfig.instance().reloadConfig();
            WDMlaConfig.instance().save();
        }
        common.endSession();
        WDMla.loadComplete();
    }

    public void serverStarting(FMLServerStartingEvent event) {}

    public void registerBuiltInServerPlugins(WDMlaCommonRegistration commonRegistration) {
        if (WDMla.isDevEnv() && WDMla.testMode == TestMode.WDMla) {
            new TestPlugin().register(commonRegistration);
        }
    }

    public void registerBuiltInClientPlugins(WDMlaClientRegistration clientRegistration) {
        new CorePlugin().registerClient(clientRegistration);
        new HarvestabilityPlugin().registerClient(clientRegistration);

        if (WDMla.isDevEnv() && WDMla.testMode == TestMode.WDMla) {
            new TestPlugin().registerClient(clientRegistration);
        }
    }
}

package com.gtnewhorizons.wdmla.api;

// Example code to pass this plugin to WDMla in FMLInitializationEvent:
// FMLInterModComms.sendMessage("WDMla", "registerPlugin",
// "com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityPlugin");
public interface IWDMlaPlugin {

    default void register(IWDMlaCommonRegistration registration) {

    }

    default void registerClient(IWDMlaClientRegistration registration) {

    }
}

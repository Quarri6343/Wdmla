package com.gtnewhorizons.wdmla.addon.harvestability;

import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;

public class HarvestabilityConfigProvider implements IConfigProvider {

//    static {
//        configOptions.put("harvestability.harvestlevel", true);
//        configOptions.put("harvestability.harvestlevelnum", false);
//        configOptions.put("harvestability.effectivetool", true);
//        configOptions.put("harvestability.currentlyharvestable", true);
//        configOptions.put("harvestability.harvestlevel.sneakingonly", false);
//        configOptions.put("harvestability.harvestlevelnum.sneakingonly", false);
//        configOptions.put("harvestability.effectivetool.sneakingonly", false);
//        configOptions.put("harvestability.currentlyharvestable.sneakingonly", false);
//        configOptions.put("harvestability.oresonly", false);
//        configOptions.put("harvestability.minimal", false);
//        configOptions.put("harvestability.unharvestableonly", false);
//        configOptions.put("harvestability.toolrequiredonly", true);
//        configOptions.put("harvestability.shearability", true);
//        configOptions.put("harvestability.shearability.sneakingonly", false);
//        configOptions.put("harvestability.silktouchability", true);
//        configOptions.put("harvestability.silktouchability.sneakingonly", false);
//    }

    @Override
    public String categoryName() {
        return HarvestabilityIdentifiers.CONFIG_CATEGORY;
    }

    @Override
    public void loadConfig(IPluginConfig config) {
        config.getCategory(HarvestabilityIdentifiers.CONFIG_CATEGORY).setComment("Waila Harvestability");
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL);
    }
}

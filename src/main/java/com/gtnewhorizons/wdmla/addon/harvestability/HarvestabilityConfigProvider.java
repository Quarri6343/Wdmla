package com.gtnewhorizons.wdmla.addon.harvestability;

import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;

public class HarvestabilityConfigProvider implements IConfigProvider {

    @Override
    public String categoryName() {
        return HarvestabilityIdentifiers.CONFIG_CATEGORY;
    }

    @Override
    public void loadConfig(IPluginConfig config) {
        config.getCategory(HarvestabilityIdentifiers.CONFIG_CATEGORY)
                .setComment(HarvestabilityIdentifiers.CONFIG_CATEGORY_COMMENT);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL_NUM);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_EFFECTIVE_TOOL);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_CURRENTLY_HARVESTABLE);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL_SNEAKING_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL_NUM_SNEAKING_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_EFFECTIVE_TOOL_SNEAKING_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_CURRENTLY_HARVESTABLE_SNEAKING_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_ORES_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_MINIMAL);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_UNHARVESTABLE_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_TOOL_REQUIRED_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_SHEARABILITY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_SHEARABILITY_SNEAKING_ONLY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_SILKTOUCHABILITY);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_SILKTOUCHABILITY_SNEAKING_ONLY);

        config.getString(HarvestabilityIdentifiers.CONFIG_MINIMAL_SEPARATOR_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_LEGACY_CURRENTLY_HARVESTABLE_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_LEGACY_NOT_CURRENTLY_HARVESTABLE_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_SHEARABILITY_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_SILK_TOUCHABILITY_STRING);

        config.getString(HarvestabilityIdentifiers.CONFIG_NEW_CURRENTLY_HARVESTABLE_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_NEW_NOT_CURRENTLY_HARVESTABLE_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_SHEARABILITY_ITEM);
        config.getString(HarvestabilityIdentifiers.CONFIG_SILKTOUCHABILITY_ITEM);
    }
}

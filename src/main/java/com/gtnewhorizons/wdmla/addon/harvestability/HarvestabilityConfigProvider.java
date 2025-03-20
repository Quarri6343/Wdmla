package com.gtnewhorizons.wdmla.addon.harvestability;

import com.gtnewhorizons.wdmla.api.IConfigProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;

public class HarvestabilityConfigProvider implements IConfigProvider {

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

        config.getString(HarvestabilityIdentifiers.CONFIG_MODERN_CURRENTLY_HARVESTABLE_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_MODERN_NOT_CURRENTLY_HARVESTABLE_STRING);
        config.getString(HarvestabilityIdentifiers.CONFIG_SHEARABILITY_ITEM);
        config.getString(HarvestabilityIdentifiers.CONFIG_SILKTOUCHABILITY_ITEM);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_MODERN_HARVEST_LEVEL_NUM);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_MODERN_SHOW_HARVESTABLE_ICON);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_MODERN_SHOW_HARVESTABLE_TOOL_ICON);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_MODERN_SHOW_SHEARABILITY_ICON);
        config.getBoolean(HarvestabilityIdentifiers.CONFIG_MODERN_SHOW_SILKTOUCHABILITY_ICON);

        config.getCategory(HarvestabilityIdentifiers.CONFIG_CATEGORY_MODERN_TINKERS)
                .setComment(HarvestabilityIdentifiers.CONFIG_CATEGORY_MODERN_TINKERS_COMMENT);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_0);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_1);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_2);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_3);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_4);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_5);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_6);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_7);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_8);
        config.getInteger(HarvestabilityIdentifiers.CONFIG_TINKERS_PICKAXE_ICON_9);
    }
}

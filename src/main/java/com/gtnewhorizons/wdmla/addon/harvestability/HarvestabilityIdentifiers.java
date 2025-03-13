package com.gtnewhorizons.wdmla.addon.harvestability;

import com.gtnewhorizons.wdmla.api.ConfigEntry;

public class HarvestabilityIdentifiers {

    public static final String CONFIG_CATEGORY = "harvestability";
    public static final String CONFIG_CATEGORY_COMMENT = "Waila Harvestability";
    public static final ConfigEntry<Boolean> CONFIG_FORCE_LEGACY = new ConfigEntry<>(
            CONFIG_CATEGORY,
            "LegacyMode",
            false,
            "If this option is enabled the old WailaHarvestability style and config will be used");

    public static final String CONFIG_CATEGORY_LEGACY = "harvestability.legacy";
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "HarvestLevel",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_NUM = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "HarvestLevelNum",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_EFFECTIVE_TOOL = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "EffectiveTool",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_CURRENTLY_HARVESTABLE = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "CurrentlyHarvestable",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "HarvestLevelSneakingOnly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_NUM_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "HarvestLevelNumSneakingOnly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_EFFECTIVE_TOOL_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "EffectiveToolSneakingOnly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_CURRENTLY_HARVESTABLE_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "CurrentlyHarvestableSneakingOnly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_ORES_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "OresOnly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_MINIMAL = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "Minimal",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_UNHARVESTABLE_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "UnharvestableOnly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_TOOL_REQUIRED_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "ToolRequiredOnly",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHEARABILITY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "Shearability",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHEARABILITY_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "ShearabilitySneakingOnly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SILKTOUCHABILITY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "SilkTouchability",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SILKTOUCHABILITY_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "SilkTouchabilitySneakingOnly",
            false,
            "");

    public static final ConfigEntry<String> CONFIG_MINIMAL_SEPARATOR_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "MinimalSeparatorString",
            " : ",
            "");
    public static final ConfigEntry<String> CONFIG_LEGACY_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "CurrentlyHarvestableString",
            "\u2714",
            "");
    public static final ConfigEntry<String> CONFIG_LEGACY_NOT_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "NotCurrentlyHarvestableString",
            "\u2718",
            "");
    public static final ConfigEntry<String> CONFIG_SHEARABILITY_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "ShearabilityString",
            "\u2702",
            "");
    public static final ConfigEntry<String> CONFIG_SILK_TOUCHABILITY_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_LEGACY,
            "SilkTouchabilityString",
            "\u2712",
            "");

    public static final String CONFIG_CATEGORY_MODERN = "harvestability.modern";
    public static final ConfigEntry<String> CONFIG_NEW_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "CurrentlyHarvestableString",
            "✔",
            "");
    public static final ConfigEntry<String> CONFIG_NEW_NOT_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "NotCurrentlyHarvestableString",
            "✕",
            "");
    public static final ConfigEntry<String> CONFIG_SHEARABILITY_ITEM = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "ShearabilityItem",
            "minecraft:shears",
            "");
    public static final ConfigEntry<String> CONFIG_SILKTOUCHABILITY_ITEM = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "SilkTouchabilityItem",
            "minecraft:grass",
            "");
}

package com.gtnewhorizons.wdmla.addon.harvestability;

import com.gtnewhorizons.wdmla.api.ConfigEntry;
import com.gtnewhorizons.wdmla.api.Identifiers;
import net.minecraft.util.ResourceLocation;

public class HarvestabilityIdentifiers {

    public static final ResourceLocation HARVESTABILITY_ICON = Harvestability("harvestability_icon");
    public static final ResourceLocation HARVESTABILITY_TEXT = Harvestability("harvestability_text");

    public static final ResourceLocation HARVESTABILITY = Harvestability("modern");
    public static final ResourceLocation LEGACY_HARVESTABILITY = Harvestability("legacy");

    public static final String CONFIG_CATEGORY = Identifiers.CONFIG_PLUGINS + ".harvestability";
    public static final String CONFIG_CATEGORY_COMMENT = "Waila Harvestability";

    public static final String CONFIG_CATEGORY_LEGACY = CONFIG_CATEGORY + ".legacy";
    public static final String CONFIG_CATEGORY_LEGACY_COMMENT = "Only available on legacy mode provider";
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

    public static final String CONFIG_CATEGORY_MODERN = CONFIG_CATEGORY + ".modern";
    public static final String CONFIG_CATEGORY_MODERN_COMMENT = "Only available on modern mode provider";
    public static final ConfigEntry<String> CONFIG_NEW_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "CurrentlyHarvestableString",
            "✔",
            "The string below the Harvest Tool icon after the item name");
    public static final ConfigEntry<String> CONFIG_NEW_NOT_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "NotCurrentlyHarvestableString",
            "✕",
            "The string below the Harvest Tool icon after the item name");
    public static final ConfigEntry<String> CONFIG_SHEARABILITY_ITEM = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "ShearabilityItem",
            "minecraft:shears",
            "The icon after an item represents the item is shearable");
    public static final ConfigEntry<String> CONFIG_SILKTOUCHABILITY_ITEM = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "SilkTouchabilityItem",
            "minecraft:grass",
            "The icon after an item represents the item can be harvested by silk touch");
    public static final ConfigEntry<Boolean> CONFIG_MODERN_HARVEST_LEVEL_NUM = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN,
            "HarvestLevelNum",
            false,
            "Shows the Harvest Level number text without enabling legacy mode");

    public static final String CONFIG_CATEGORY_MODERN_TINKERS = CONFIG_CATEGORY_MODERN + ".tinkersconstruct";
    public static final String CONFIG_CATEGORY_MODERN_TINKERS_COMMENT = "IDs of the TiC effective pickaxe material corresponding to the harvest level.\n" +
            "Note that the default values are tuned for GTNH Iguana tweaks (TiC itself only has the harvest level up to 6)";
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_0 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel0",
            0,
            "default: wood");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_1 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel1",
            13,
            "default: copper");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_2 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel2",
            2,
            "default: iron");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_3 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel3",
            14,
            "default: tin");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_4 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel4",
            16,
            "default: redstone");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_5 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel5",
            6,
            "default: obsidian");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_6 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel6",
            11,
            "default: ardite");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_7 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel7",
            10,
            "default: cobalt");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_8 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel8",
            12,
            "default: manyullyn");
    public static final ConfigEntry<Integer> CONFIG_TINKERS_PICKAXE_ICON_9 = new ConfigEntry<>(
            CONFIG_CATEGORY_MODERN_TINKERS,
            "HarvestLevel9",
            12,
            "default: manyullynplus");

    public static ResourceLocation Harvestability(String path) {
        return new ResourceLocation("harvestability", path);
    }
}

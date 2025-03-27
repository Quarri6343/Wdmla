package com.gtnewhorizons.wdmla.addon.harvestability;

import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.ConfigEntry;
import com.gtnewhorizons.wdmla.api.Identifiers;

public class HarvestabilityIdentifiers {

    // component tag
    public static final ResourceLocation HARVESTABILITY_ICON = Harvestability("harvestability_icon");
    public static final ResourceLocation HARVESTABILITY_TEXT = Harvestability("harvestability_text");

    // provider Uid
    public static final ResourceLocation HARVESTABILITY = Harvestability("modern");
    public static final ResourceLocation LEGACY_HARVESTABILITY = Harvestability("legacy");

    // config
    public static final String CONFIG_CATEGORY = Identifiers.CONFIG_PROVIDER + ".harvestability";
    public static final String CONFIG_CATEGORY_LANGKEY = "option.harvestability.category";

    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.harvestlevel",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_NUM = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.harvestlevelnum",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_EFFECTIVE_TOOL = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.effectivetool",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_CURRENTLY_HARVESTABLE = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.currentlyharvestable",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_SNEAKING_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.harvestlevel.sneakingonly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_NUM_SNEAKING_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.harvestlevelnum.sneakingonly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_EFFECTIVE_TOOL_SNEAKING_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.effectivetool.sneakingonly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_CURRENTLY_HARVESTABLE_SNEAKING_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.currentlyharvestable.sneakingonly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_ORES_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.oresonly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_MINIMAL = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.minimal",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_UNHARVESTABLE_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.unharvestableonly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_TOOL_REQUIRED_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.toolrequiredonly",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHEARABILITY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.shearability",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SHEARABILITY_SNEAKING_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.shearability.sneakingonly",
            false,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SILKTOUCHABILITY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.silktouchability",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_SILKTOUCHABILITY_SNEAKING_ONLY = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.silktouchability.sneakingonly",
            false,
            "");

    public static final ConfigEntry<String> CONFIG_MINIMAL_SEPARATOR_STRING = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.minimalseparator.string",
            " : ",
            "");
    public static final ConfigEntry<String> CONFIG_LEGACY_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.currentlyharvestable.string",
            "\u2714",
            "");
    public static final ConfigEntry<String> CONFIG_LEGACY_NOT_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.notcurrentlyharvestable.string",
            "\u2718",
            "");
    public static final ConfigEntry<String> CONFIG_SHEARABILITY_STRING = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.shearability.string",
            "\u2702",
            "");
    public static final ConfigEntry<String> CONFIG_SILK_TOUCHABILITY_STRING = new ConfigEntry<>(
            LegacyHarvestToolProvider.INSTANCE,
            "option.harvestability.silktouchability.string",
            "\u2712",
            "");

    public static final ConfigEntry<String> CONFIG_MODERN_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.currentlyharvestable.string",
            "✔",
            "The string below the Harvest Tool icon after the item name");
    public static final ConfigEntry<String> CONFIG_MODERN_NOT_CURRENTLY_HARVESTABLE_STRING = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.notcurrentlyharvestable.string",
            "✕",
            "The string below the Harvest Tool icon after the item name");
    public static final ConfigEntry<String> CONFIG_SHEARABILITY_ITEM = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.shearability.item",
            "minecraft:shears",
            "The icon after an item represents the item is shearable");
    public static final ConfigEntry<String> CONFIG_SILKTOUCHABILITY_ITEM = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.silktouchability.item",
            "minecraft:grass",
            "The icon after an item represents the item can be harvested by silk touch");
    public static final ConfigEntry<Boolean> CONFIG_MODERN_HARVEST_LEVEL_NUM = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.harvestlevelnum",
            false,
            "Shows the Harvest Level number text without enabling legacy mode");
    public static final ConfigEntry<Boolean> CONFIG_MODERN_CURRENTLY_HARVESTABLE_ICON = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.currentlyHarvestable.icon",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_MODERN_EFFECTIVE_TOOL_ICON = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.effectivetool.icon",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_MODERN_SHOW_SHEARABILITY_ICON = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "ShowShearabilityIcon",
            true,
            "");
    public static final ConfigEntry<Boolean> CONFIG_MODERN_SHOW_SILKTOUCHABILITY_ICON = new ConfigEntry<>(
            HarvestToolProvider.INSTANCE,
            "option.harvestability.silktouchability.icon",
            true,
            "");

    public static final String CONFIG_CATEGORY_MODERN_TINKERS = HarvestToolProvider.INSTANCE.getConfigCategory() + ".tinkersconstruct";
    public static final String CONFIG_CATEGORY_MODERN_TINKERS_COMMENT = "IDs of the TiC effective pickaxe material corresponding to the harvest level.\n"
            + "Note that the default values are tuned for GTNH Iguana tweaks (TiC itself only has the harvest level up to 6)";
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

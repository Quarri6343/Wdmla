package com.gtnewhorizons.wdmla.addon.harvestability;

import com.gtnewhorizons.wdmla.api.ConfigEntry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class HarvestabilityIdentifiers {

    public static final String MINIMAL_SEPARATOR_STRING = " : ";

    public static final String CURRENTLY_HARVESTABLE_STRING = "\u2714";

    public static final String NOT_CURRENTLY_HARVESTABLE_STRING = "\u2718";

    public static final String SHEARABILITY_STRING = "\u2702";

    public static final String SILK_TOUCHABILITY_STRING = "\u2712";

    public static final String CHECK = "✔";
    public static final String X = "✕";
    public static final ItemStack SHEARABILITY_ICON = new ItemStack(Items.shears);
    public static final ItemStack SILKTOUCH_ICON = new ItemStack(Blocks.grass);

    public static final String CONFIG_CATEGORY = "harvestability";
    public static final String CONFIG_CATEGORY_COMMENT = "Waila Harvestability";
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL = new ConfigEntry<>(
            CONFIG_CATEGORY, "HarvestLevel", true, "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_NUM = new ConfigEntry<>(
            CONFIG_CATEGORY, "HarvestLevelNum", false, "");
    public static final ConfigEntry<Boolean> CONFIG_EFFECTIVE_TOOL = new ConfigEntry<>(
            CONFIG_CATEGORY, "EffectiveTool", true, "");
    public static final ConfigEntry<Boolean> CONFIG_CURRENTLY_HARVESTABLE = new ConfigEntry<>(
            CONFIG_CATEGORY, "CurrentlyHarvestable", true, "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "HarvestLevelSneakingOnly", false, "");
    public static final ConfigEntry<Boolean> CONFIG_HARVEST_LEVEL_NUM_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "HarvestLevelNumSneakingOnly", false, "");
    public static final ConfigEntry<Boolean> CONFIG_EFFECTIVE_TOOL_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "EffectiveToolSneakingOnly", false, "");
    public static final ConfigEntry<Boolean> CONFIG_CURRENTLY_HARVESTABLE_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "CurrentlyHarvestableSneakingOnly", false, "");
    public static final ConfigEntry<Boolean> CONFIG_ORES_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "OresOnly", false, "");
    public static final ConfigEntry<Boolean> CONFIG_MINIMAL = new ConfigEntry<>(
            CONFIG_CATEGORY, "Minimal", false, "");
    public static final ConfigEntry<Boolean> CONFIG_UNHARVESTABLE_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "UnharvestableOnly", false, "");
    public static final ConfigEntry<Boolean> CONFIG_TOOL_REQUIRED_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "ToolRequiredOnly", true, "");
    public static final ConfigEntry<Boolean> CONFIG_SHEARABILITY = new ConfigEntry<>(
            CONFIG_CATEGORY, "Shearability", true, "");
    public static final ConfigEntry<Boolean> CONFIG_SHEARABILITY_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "ShearabilitySneakingOnly", false, "");
    public static final ConfigEntry<Boolean> CONFIG_SILKTOUCHABILITY = new ConfigEntry<>(
            CONFIG_CATEGORY, "SilkTouchability", true, "");
    public static final ConfigEntry<Boolean> CONFIG_SILKTOUCHABILITY_SNEAKING_ONLY = new ConfigEntry<>(
            CONFIG_CATEGORY, "SilkTouchabilitySneakingOnly", false, "");

}

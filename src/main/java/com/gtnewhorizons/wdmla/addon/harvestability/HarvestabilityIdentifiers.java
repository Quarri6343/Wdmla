package com.gtnewhorizons.wdmla.addon.harvestability;

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
}

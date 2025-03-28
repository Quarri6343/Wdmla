package com.gtnewhorizons.wdmla.plugin.harvestability.helpers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyTinkersConstruct;
import com.gtnewhorizons.wdmla.api.Mods;

public class ToolHelper {

    public static final String TOOL_PICKAXE = "pickaxe";
    public static final String TOOL_SHOVEL = "shovel";
    public static final String TOOL_AXE = "axe";
    public static final String TOOL_SWORD = "sword";

    public static boolean canToolHarvestBlock(ItemStack tool, Block block, int metadata) {
        return block.getMaterial().isToolNotRequired() || tool.func_150998_b(block); // func_150998_b = canHarvestBlock
    }

    public static ItemStack getEffectiveToolIcon(String effectiveTool, int harvestLevel) {
        switch (effectiveTool) {
            case TOOL_PICKAXE:
                return getEffectivePickaxeIcon(harvestLevel);
            case TOOL_SHOVEL:
                return new ItemStack(Items.wooden_shovel);
            case TOOL_AXE:
                return new ItemStack(Items.wooden_axe);
            case TOOL_SWORD:
                return new ItemStack(Items.wooden_sword);
            default:
                if (Mods.GREGTECH.isLoaded()) {
                    return ProxyGregTech.getEffectiveGregToolIcon(effectiveTool, harvestLevel);
                } else {
                    return new ItemStack(Blocks.iron_bars);
                }
        }
    }

    public static ItemStack getEffectivePickaxeIcon(int harvestLevel) {
        if (Mods.TCONSTUCT.isLoaded()) {
            return ProxyTinkersConstruct.getEffectivePickaxeIcon(harvestLevel);
        }
        return getVanillaEffectivePickaxeIcon(harvestLevel);
    }

    private static @NotNull ItemStack getVanillaEffectivePickaxeIcon(int harvestLevel) {
        return switch (harvestLevel) {
            case 0 -> new ItemStack(Items.wooden_pickaxe);
            case 1 -> new ItemStack(Items.stone_pickaxe);
            case 2 -> new ItemStack(Items.iron_pickaxe);
            case 3 -> new ItemStack(Items.diamond_pickaxe);
            default -> new ItemStack(Blocks.iron_bars);
        };
    }
}

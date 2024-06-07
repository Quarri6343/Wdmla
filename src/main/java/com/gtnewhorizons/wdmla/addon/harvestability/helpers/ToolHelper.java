package com.gtnewhorizons.wdmla.addon.harvestability.helpers;

import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyIguanaTweaks;
import com.gtnewhorizons.wdmla.api.Mods;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ToolHelper {
    public static final String PICKAXE = "pickaxe";
    public static final String SHOVEL = "shovel";
    public static final String AXE = "axe";
    public static final String SWORD = "sword";

    public static boolean canToolHarvestBlock(ItemStack tool, Block block, int metadata) {
        return block.getMaterial().isToolNotRequired() || tool.func_150998_b(block); // func_150998_b = canHarvestBlock
    }

    public static ItemStack getEffectiveToolIcon(String effectiveTool, int harvestLevel) {
        switch (effectiveTool) {
            case PICKAXE:
                return getEffectivePickaxeIcon(harvestLevel);
            case SHOVEL:
                return new ItemStack(Items.wooden_shovel);
            case AXE:
                return new ItemStack(Items.wooden_axe);
            case SWORD:
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
        if (Mods.IGUANATWEAKS.isLoaded()) {
            return ProxyIguanaTweaks.getEffectivePickaxeIcon(harvestLevel);
        }
        return switch (harvestLevel) {
            case 0 -> new ItemStack(Items.wooden_pickaxe);
            case 1 -> new ItemStack(Items.stone_pickaxe);
            case 2 -> new ItemStack(Items.iron_pickaxe);
            case 3 -> new ItemStack(Items.diamond_pickaxe);
            default -> new ItemStack(Blocks.iron_bars);
        };
    }
}

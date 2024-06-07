package com.gtnewhorizons.wdmla.addon.harvestability.helpers;

import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyIguanaTweaks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeHooks;

public class ToolHelper {

    public static boolean canToolHarvestBlock(ItemStack tool, Block block, int metadata) {
        return block.getMaterial().isToolNotRequired() || tool.func_150998_b(block); // func_150998_b = canHarvestBlock
    }

    public static ItemStack getEffectiveToolIcon(String effectiveTool, int harvestLevel) {
        switch (effectiveTool) {
            case "pickaxe":
                return getEffectivePickaxeIcon(harvestLevel);
            case "shovel":
                return new ItemStack(Items.wooden_shovel);
            case "axe":
                return new ItemStack(Items.wooden_axe);
            case "sword":
                return new ItemStack(Items.wooden_sword);
            default:
                if (ProxyGregTech.isModLoaded) {
                    return ProxyGregTech.getEffectiveGregToolIcon(effectiveTool, harvestLevel);
                } else {
                    return new ItemStack(Blocks.iron_bars);
                }
        }
    }

    public static ItemStack getEffectivePickaxeIcon(int harvestLevel) {
        if (ProxyIguanaTweaks.isModLoaded) {
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

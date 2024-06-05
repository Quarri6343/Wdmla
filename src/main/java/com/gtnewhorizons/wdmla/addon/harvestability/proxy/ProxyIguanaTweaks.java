package com.gtnewhorizons.wdmla.addon.harvestability.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ProxyIguanaTweaks {

    private static Class<?> HarvestLevels = null;
    private static Method proxyGetHarvestLevelName;
    private static boolean isVanilla = false;
    private static final List<ItemStack> creativePickaxes = new ArrayList<>();

    public static void init() {
        try {
            HarvestLevels = Class.forName("iguanaman.iguanatweakstconstruct.util.HarvestLevels");
            proxyGetHarvestLevelName = HarvestLevels.getDeclaredMethod("getHarvestLevelName", int.class);
            Field vanillaField = HarvestLevels.getDeclaredField("vanilla");
            vanillaField.setAccessible(true);
            isVanilla = vanillaField.getBoolean(null);
            GameRegistry.findItem("TConstruct", "pickaxe").getSubItems(null, null, creativePickaxes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getHarvestLevelName(int num) {
        String harvestLevelName = "<Unknown>";

        try {
            harvestLevelName = (String) proxyGetHarvestLevelName.invoke(null, num);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return harvestLevelName;
    }

    public static ItemStack getHarvestLevelIcon(int num) {
        if(isVanilla) {
            return switch (num) {
                case 0 -> creativePickaxes.get(0); //any
                case 1 -> creativePickaxes.get(2); //iron
                case 2 -> creativePickaxes.get(16); //redstone
                case 3 -> creativePickaxes.get(6); //obsidian
                case 4 -> creativePickaxes.get(19); //cobalt
                case 5 -> creativePickaxes.get(12); //manyullyn
                case 6 -> creativePickaxes.get(12); //manyullyn+
                default -> new ItemStack(Blocks.iron_bars);
            };
        }
        else {
            return switch (num) {
                case 0 -> creativePickaxes.get(0); //any
                case 1 -> creativePickaxes.get(13); //copper
                case 2 -> creativePickaxes.get(2); //iron
                case 3 -> creativePickaxes.get(14); //tin
                case 4 -> creativePickaxes.get(16); //redstone
                case 5 -> creativePickaxes.get(6); //obsidian
                case 6 -> creativePickaxes.get(11); //ardite
                case 7 -> creativePickaxes.get(10); //cobalt
                case 8 -> creativePickaxes.get(12); //manyullyn
                case 9 -> creativePickaxes.get(12); //manyullyn+
                default -> new ItemStack(Blocks.iron_bars);
            };
        }
    }
}

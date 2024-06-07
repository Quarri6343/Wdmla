package com.gtnewhorizons.wdmla.addon.harvestability.proxy;

import static com.gtnewhorizons.wdmla.addon.harvestability.proxy.TinkersHarvestMaterialIDs.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

public class ProxyIguanaTweaks {

    public static final String MODID = "IguanaTweaksTConstruct";
    private static Class<?> HarvestLevels = null;
    private static Method proxyGetHarvestLevelName;
    private static boolean isVanilla = false;
    private static final List<ItemStack> creativePickaxes = new ArrayList<>();
    public static final boolean isModLoaded = Loader.isModLoaded(MODID);

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

    public static ItemStack getEffectivePickaxeIcon(int num) {
        if (isVanilla) {
            return switch (num) {
                case 0 -> creativePickaxes.get(ANY);
                case 1 -> creativePickaxes.get(IRON);
                case 2 -> creativePickaxes.get(REDSTONE);
                case 3 -> creativePickaxes.get(OBSIDIAN);
                case 4 -> creativePickaxes.get(COBALT);
                case 5 -> creativePickaxes.get(MANYULLYN);
                case 6 -> creativePickaxes.get(MANYULLYNPLUS);
                default -> new ItemStack(Blocks.iron_bars);
            };
        } else {
            return switch (num) {
                case 0 -> creativePickaxes.get(ANY);
                case 1 -> creativePickaxes.get(COPPER);
                case 2 -> creativePickaxes.get(IRON);
                case 3 -> creativePickaxes.get(TIN);
                case 4 -> creativePickaxes.get(REDSTONE);
                case 5 -> creativePickaxes.get(OBSIDIAN);
                case 6 -> creativePickaxes.get(ARDITE);
                case 7 -> creativePickaxes.get(COBALT);
                case 8 -> creativePickaxes.get(MANYULLYN);
                case 9 -> creativePickaxes.get(MANYULLYNPLUS);
                default -> new ItemStack(Blocks.iron_bars);
            };
        }
    }
}

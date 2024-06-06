package com.gtnewhorizons.wdmla.addon.harvestability.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * We support GregTech 5 tool stats on our side to support non-GTNH GT5-unofficial
 */
public class ProxyGregTech {

    public static final String modID = "gregtech";
    public static final String oreBlockID = "gt.blockores";
    public static final String oreBlockUniqueIdentifier = modID + ":" + oreBlockID;
    public static final String casingID = "gt.blockcasings";
    public static final String casingUniqueIdentifier = modID + ":" + casingID;
    public static final String machineID = "gt.blockmachines";
    public static final String machineUniqueIdentifier = modID + ":" + machineID;
    public static boolean isModLoaded = Loader.isModLoaded(modID);

    private static short Wrench;
    private static short WireCutter;
    private static ItemStack ironWrench;
    private static ItemStack steelWrench;
    private static ItemStack ironWireCutter;

    @SuppressWarnings("unchecked")
    public static void init() {
        try {
            Class<?> GT_MetaGenerated_Tool = Class.forName("gregtech.api.items.GT_MetaGenerated_Tool");
            Field sInstancesField = GT_MetaGenerated_Tool.getField("sInstances");
            ConcurrentHashMap<String, ?> sInstances = (ConcurrentHashMap<String, ?>) (sInstancesField.get(null));
            Object metaTool01 = sInstances.get("gt.metatool.01");
            Class<?> Materials = Class.forName("gregtech.api.enums.Materials");
            Method getToolWithStatsMethod = GT_MetaGenerated_Tool
                    .getDeclaredMethod("getToolWithStats", int.class, int.class, Materials, Materials, long[].class);
            Class<?> GT_MetaGenerated_Tool_01 = Class.forName("gregtech.common.items.GT_MetaGenerated_Tool_01");

            Field WrenchField = GT_MetaGenerated_Tool_01.getField("WRENCH");
            Wrench = WrenchField.getShort(null);
            Field WireCutterField = GT_MetaGenerated_Tool_01.getField("WIRECUTTER");
            WireCutter = WireCutterField.getShort(null);

            Object ironMaterial = Materials.getField("Iron").get(null);
            Object steelMaterial = Materials.getField("Steel").get(null);

            ironWrench = (ItemStack) getToolWithStatsMethod
                    .invoke(metaTool01, Wrench, 1, ironMaterial, ironMaterial, null);
            steelWrench = (ItemStack) getToolWithStatsMethod
                    .invoke(metaTool01, Wrench, 1, steelMaterial, steelMaterial, null);
            ironWireCutter = (ItemStack) getToolWithStatsMethod
                    .invoke(metaTool01, WireCutter, 1, ironMaterial, ironMaterial, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isOreBlock(Block block) {
        return isModLoaded && GameRegistry.findUniqueIdentifierFor(block).toString().equals(oreBlockUniqueIdentifier);
    }

    public static boolean isCasing(Block block) {
        return isModLoaded && GameRegistry.findUniqueIdentifierFor(block).toString().equals(casingUniqueIdentifier);
    }

    public static boolean isMachine(Block block) {
        return isModLoaded && GameRegistry.findUniqueIdentifierFor(block).toString().equals(machineUniqueIdentifier);
    }

    public static boolean isGTTool(ItemStack itemStack) {
        return isModLoaded && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("GT.ToolStats");
    }

    public static boolean isWrench(ItemStack itemStack) {
        return isGTTool(itemStack) && itemStack.getItemDamage() == Wrench;
    }

    public static boolean isWireCutter(ItemStack itemStack) {
        return isGTTool(itemStack) && itemStack.getItemDamage() == WireCutter;
    }

    public static ItemStack getEffectiveGregToolIcon(String effectiveTool, int harvestLevel) {
        return switch (effectiveTool) {
            case "wrench" -> getEffectiveWrenchIcon(harvestLevel);
            case "cutter" -> getEffectiveCutterIcon(harvestLevel);
            default -> new ItemStack(Blocks.iron_bars);
        };
    }

    public static ItemStack getEffectiveWrenchIcon(int num) {
        return switch (num) {
            case 0, 1, 2 -> ironWrench;
            case 3, 4 -> steelWrench; // idk does 4 actually exist though.
            default -> new ItemStack(Blocks.iron_bars);
        };
    }

    public static ItemStack getEffectiveCutterIcon(int num) {
        return switch (num) {
            case 0, 1, 2 -> ironWireCutter;
            default -> new ItemStack(Blocks.iron_bars);
        };
    }
}

package com.gtnewhorizons.wdmla.addon.harvestability.proxy;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.gtnewhorizons.wdmla.api.Mods;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * We support GregTech 5 tool stats on our side to support non-GTNH GT5-unofficial
 */
public class ProxyGregTech {

    public static final String CASING_ID = "gt.blockcasings";
    public static final String CASING_UNIQUE_IDENTIFIER = Mods.GREGTECH.modID + ":" + CASING_ID;
    public static final String MACHINE_ID = "gt.blockmachines";
    public static final String MACHINE_UNIQUE_IDENTIFIER = Mods.GREGTECH.modID + ":" + MACHINE_ID;

    public static final String TOOL_WRENCH = "wrench";
    public static final String TOOL_WIRE_CUTTER = "cutter";

    private static int wrench;
    private static int wireCutter;
    private static ItemStack ironWrench;
    private static ItemStack steelWrench;
    private static ItemStack ironWireCutter;

    @SuppressWarnings("unchecked")
    public static void init() {
        try {
            Class<?> GT_MetaGenerated_Tool = Class.forName("gregtech.api.items.MetaGeneratedTool");
            Field sInstancesField = GT_MetaGenerated_Tool.getField("sInstances");
            ConcurrentHashMap<String, ?> sInstances = (ConcurrentHashMap<String, ?>) (sInstancesField.get(null));
            Object metaTool01 = sInstances.get("gt.metatool.01");
            Class<?> Materials = Class.forName("gregtech.api.enums.Materials");
            Method getToolWithStatsMethod = GT_MetaGenerated_Tool
                    .getDeclaredMethod("getToolWithStats", int.class, int.class, Materials, Materials, long[].class);
            Class<?> GT_MetaGenerated_Tool_01 = Class.forName("gregtech.common.items.IDMetaTool01");

            Field idField = GT_MetaGenerated_Tool_01.getField("ID");
            Object wrenchConstant = Enum.valueOf((Class<Enum>) GT_MetaGenerated_Tool_01, "WRENCH");
            wrench = idField.getInt(wrenchConstant);
            Object wireCutterConstant = Enum.valueOf((Class<Enum>) GT_MetaGenerated_Tool_01, "WIRECUTTER");
            wireCutter = idField.getInt(wireCutterConstant);

            Object ironMaterial = Materials.getField("Iron").get(null);
            Object steelMaterial = Materials.getField("Steel").get(null);

            ironWrench = (ItemStack) getToolWithStatsMethod
                    .invoke(metaTool01, wrench, 1, ironMaterial, ironMaterial, null);
            steelWrench = (ItemStack) getToolWithStatsMethod
                    .invoke(metaTool01, wrench, 1, steelMaterial, steelMaterial, null);
            ironWireCutter = (ItemStack) getToolWithStatsMethod
                    .invoke(metaTool01, wireCutter, 1, ironMaterial, ironMaterial, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Use a nested class so that ProxyGregTech can load without loading GTMethods. This allows us to keep GTUTIL_IS_ORE
     * static final without hacky code (MethodHandles have zero runtime cost if they're stored in a static final field).
     */
    private static class GTMethods {

        public static final MethodHandle GTUTIL_IS_ORE;

        static {
            try {
                GTUTIL_IS_ORE = MethodHandles.lookup().findStatic(
                        Class.forName("gregtech.api.util.GTUtility"),
                        "isOre",
                        MethodType.methodType(boolean.class, Block.class, int.class));
            } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isOreBlock(Block block, int meta) {
        if (!Mods.GREGTECH.isLoaded()) return false;

        try {
            // loads GTMethods
            return (boolean) GTMethods.GTUTIL_IS_ORE.invokeExact(block, meta);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isCasing(Block block) {
        return Mods.GREGTECH.isLoaded()
                && GameRegistry.findUniqueIdentifierFor(block).toString().equals(CASING_UNIQUE_IDENTIFIER);
    }

    public static boolean isMachine(Block block) {
        return Mods.GREGTECH.isLoaded()
                && GameRegistry.findUniqueIdentifierFor(block).toString().equals(MACHINE_UNIQUE_IDENTIFIER);
    }

    public static boolean isGTTool(ItemStack itemStack) {
        return Mods.GREGTECH.isLoaded() && itemStack.hasTagCompound()
                && itemStack.getTagCompound().hasKey("GT.ToolStats");
    }

    public static boolean isWrench(ItemStack itemStack) {
        return isGTTool(itemStack) && itemStack.getItemDamage() == wrench;
    }

    public static boolean isWireCutter(ItemStack itemStack) {
        return isGTTool(itemStack) && itemStack.getItemDamage() == wireCutter;
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

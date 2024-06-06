package com.gtnewhorizons.wdmla.addon.harvestability.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityIdentifiers;

import mcp.mobius.waila.api.IWailaConfigHandler;

public class BlockHelper {

    private static final HashMap<String, ItemStack> testTools = new HashMap<String, ItemStack>();
    static {
        testTools.put("pickaxe", new ItemStack(Items.wooden_pickaxe));
        testTools.put("shovel", new ItemStack(Items.wooden_shovel));
        testTools.put("axe", new ItemStack(Items.wooden_axe));
    }

    public static String getEffectiveToolOf(World world, int x, int y, int z, Block block, int metadata) {
        String effectiveTool = block.getHarvestTool(metadata);
        if (effectiveTool == null) {
            float hardness = block.getBlockHardness(world, x, y, z);
            if (hardness > 0f) {
                for (Map.Entry<String, ItemStack> testToolEntry : testTools.entrySet()) {
                    ItemStack testTool = testToolEntry.getValue();
                    if (testTool != null && testTool.getItem() instanceof ItemTool
                            && testTool.func_150997_a(block) >= ((ItemTool) testTool.getItem()).func_150913_i()
                                    .getEfficiencyOnProperMaterial()) {
                        effectiveTool = testToolEntry.getKey();
                        break;
                    }
                }
            }
        }
        return effectiveTool;
    }

    public static boolean isBlockUnbreakable(Block block, World world, int x, int y, int z) {
        return block.getBlockHardness(world, x, y, z) == -1.0f;
    }

    public static String getShearabilityString(EntityPlayer player, Block block, int meta,
            MovingObjectPosition position, IWailaConfigHandler config) {
        boolean isSneaking = player.isSneaking();
        boolean showShearability = config.getConfig("harvestability.shearability")
                && (!config.getConfig("harvestability.shearability.sneakingonly") || isSneaking);

        if (showShearability && (block instanceof IShearable || block == Blocks.deadbush
                || (block == Blocks.double_plant && block.getItemDropped(meta, new Random(), 0) == null))) {
            ItemStack itemHeld = player.getHeldItem();
            boolean isHoldingShears = itemHeld != null && itemHeld.getItem() instanceof ItemShears;
            boolean isShearable = isHoldingShears && ((IShearable) block)
                    .isShearable(itemHeld, player.worldObj, position.blockX, position.blockY, position.blockZ);
            return ColorHelper.getBooleanColor(isShearable, !isShearable && isHoldingShears)
                    + HarvestabilityIdentifiers.SHEARABILITY_STRING;
        }
        return "";
    }

    public static String getSilkTouchabilityString(EntityPlayer player, Block block, int meta,
            MovingObjectPosition position, IWailaConfigHandler config) {
        boolean isSneaking = player.isSneaking();
        boolean showSilkTouchability = config.getConfig("harvestability.silktouchability")
                && (!config.getConfig("harvestability.silktouchability.sneakingonly") || isSneaking);

        if (showSilkTouchability && block
                .canSilkHarvest(player.worldObj, player, position.blockX, position.blockY, position.blockZ, meta)) {
            Item itemDropped = block.getItemDropped(meta, new Random(), 0);
            boolean silkTouchMatters = (itemDropped instanceof ItemBlock && itemDropped != Item.getItemFromBlock(block))
                    || block.quantityDropped(new Random()) <= 0;
            if (silkTouchMatters) {
                boolean hasSilkTouch = EnchantmentHelper.getSilkTouchModifier(player);
                return ColorHelper.getBooleanColor(hasSilkTouch) + HarvestabilityIdentifiers.SILK_TOUCHABILITY_STRING;
            }
        }
        return "";
    }
}

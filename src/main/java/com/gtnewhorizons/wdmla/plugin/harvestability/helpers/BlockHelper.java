package com.gtnewhorizons.wdmla.plugin.harvestability.helpers;

import static com.gtnewhorizons.wdmla.plugin.harvestability.helpers.ToolHelper.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.config.PluginsConfig;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyGregTech;

public class BlockHelper {

    private static final HashMap<String, ItemStack> testTools = new HashMap<>();
    static {
        testTools.put(TOOL_PICKAXE, new ItemStack(Items.wooden_pickaxe));
        testTools.put(TOOL_SHOVEL, new ItemStack(Items.wooden_shovel));
        testTools.put(TOOL_AXE, new ItemStack(Items.wooden_axe));
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

    public static IComponent getShearabilityString(EntityPlayer player, Block block, int meta,
            MovingObjectPosition position) {
        boolean isSneaking = player.isSneaking();
        boolean showShearability = PluginsConfig.harvestability.legacy.shearability
                && (!PluginsConfig.harvestability.legacy.shearabilitySneakingOnly || isSneaking);

        if (showShearability && (block instanceof IShearable || block == Blocks.deadbush
                || (block == Blocks.double_plant && block.getItemDropped(meta, new Random(), 0) == null))) {
            ItemStack itemHeld = player.getHeldItem();
            boolean isHoldingShears = itemHeld != null && itemHeld.getItem() instanceof ItemShears;
            boolean isShearable = isHoldingShears && ((IShearable) block)
                    .isShearable(itemHeld, player.worldObj, position.blockX, position.blockY, position.blockZ);
            String shearableString = PluginsConfig.harvestability.legacy.shearabilityString;
            return isShearable ? ThemeHelper.INSTANCE.success(shearableString)
                    : ThemeHelper.INSTANCE.failure(shearableString);
        }
        return null;
    }

    public static IComponent getSilkTouchabilityString(EntityPlayer player, Block block, int meta,
            MovingObjectPosition position) {
        boolean isSneaking = player.isSneaking();
        boolean showSilkTouchability = PluginsConfig.harvestability.legacy.silkTouchability
                && (!PluginsConfig.harvestability.legacy.silkTouchabilitySneakingOnly || isSneaking);

        if (showSilkTouchability && block
                .canSilkHarvest(player.worldObj, player, position.blockX, position.blockY, position.blockZ, meta)) {
            Item itemDropped = block.getItemDropped(meta, new Random(), 0);
            boolean silkTouchMatters = (itemDropped instanceof ItemBlock && itemDropped != Item.getItemFromBlock(block))
                    || block.quantityDropped(new Random()) <= 0;
            if (silkTouchMatters) {
                boolean hasSilkTouch = EnchantmentHelper.getSilkTouchModifier(player);
                String silkTouchString = PluginsConfig.harvestability.legacy.silkTouchabilityString;
                return hasSilkTouch ? ThemeHelper.INSTANCE.success(silkTouchString)
                        : ThemeHelper.INSTANCE.failure(silkTouchString);
            }
        }
        return null;
    }

    public static Block getEffectiveBlock(Block block, ItemStack itemForm, int meta) {
        return isDisguised(block, itemForm, meta) ? Block.getBlockFromItem(itemForm.getItem()) : block;
    }

    public static int getEffectiveMeta(Block block, ItemStack itemForm, int meta) {
        return isDisguised(block, itemForm, meta) ? itemForm.getItemDamage() : meta;
    }

    public static boolean isDisguised(Block block, ItemStack itemForm, int meta) {
        return itemForm.getItem() instanceof ItemBlock && !ProxyGregTech.isOreBlock(block, meta)
                && !ProxyGregTech.isCasing(block)
                && !ProxyGregTech.isMachine(block);
    }
}

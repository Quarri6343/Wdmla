package com.gtnewhorizons.wdmla.addon.harvestability;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeHooks;

import com.gtnewhorizons.wdmla.addon.harvestability.helpers.BlockHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.helpers.ColorHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.helpers.OreHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.helpers.StringHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.helpers.ToolHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyCreativeBlocks;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyTinkersConstruct;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;

public class LegacyHarvestToolProvider implements IBlockComponentProvider {

    @Override
    public ResourceLocation getUid() {
        return HarvestabilityIdentifiers.LEGACY_HARVESTABILITY;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HARVESTABILITY_OVERRIDE;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (ProxyCreativeBlocks.isCreativeBlock(accessor.getBlock(), accessor.getMetadata())) {
            return;
        }

        Block effectiveBlock = BlockHelper
                .getEffectiveBlock(accessor.getBlock(), accessor.getItemForm(), accessor.getMetadata());
        int effectiveMeta = BlockHelper
                .getEffectiveMeta(accessor.getBlock(), accessor.getItemForm(), accessor.getMetadata());

        List<String> stringParts = new ArrayList<>();
        getLegacyHarvestability(
                stringParts,
                accessor.getPlayer(),
                effectiveBlock,
                effectiveMeta,
                accessor.getHitResult(),
                config);
        updateTooltip(stringParts, tooltip, config);
    }

    private void updateTooltip(List<String> stringParts, ITooltip tooltip, IPluginConfig config) {
        boolean minimalLayout = config.getBoolean(HarvestabilityIdentifiers.CONFIG_MINIMAL);
        String separator = config.getString(HarvestabilityIdentifiers.CONFIG_MINIMAL_SEPARATOR_STRING);
        if (!stringParts.isEmpty()) {
            if (minimalLayout)
                tooltip.text(StringHelper.concatenateStringList(stringParts, EnumChatFormatting.RESET + separator));
            else for (String stringPart : stringParts) {
                tooltip.text(stringPart);
            }
        }
    }

    public void getLegacyHarvestability(List<String> stringList, EntityPlayer player, Block block, int meta,
            MovingObjectPosition position, IPluginConfig config) {
        boolean minimalLayout = config.getBoolean(HarvestabilityIdentifiers.CONFIG_MINIMAL);
        boolean isSneaking = player.isSneaking();
        boolean showHarvestLevel = config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL)
                && (!config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL_SNEAKING_ONLY) || isSneaking);
        boolean showHarvestLevelNum = config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL_NUM)
                && (!config.getBoolean(HarvestabilityIdentifiers.CONFIG_HARVEST_LEVEL_NUM_SNEAKING_ONLY) || isSneaking);
        boolean showEffectiveTool = config.getBoolean(HarvestabilityIdentifiers.CONFIG_EFFECTIVE_TOOL)
                && (!config.getBoolean(HarvestabilityIdentifiers.CONFIG_EFFECTIVE_TOOL_SNEAKING_ONLY) || isSneaking);
        boolean showCurrentlyHarvestable = config.getBoolean(HarvestabilityIdentifiers.CONFIG_CURRENTLY_HARVESTABLE)
                && (!config.getBoolean(HarvestabilityIdentifiers.CONFIG_CURRENTLY_HARVESTABLE_SNEAKING_ONLY)
                        || isSneaking);
        boolean hideWhileHarvestable = config.getBoolean(HarvestabilityIdentifiers.CONFIG_UNHARVESTABLE_ONLY);
        boolean showOresOnly = config.getBoolean(HarvestabilityIdentifiers.CONFIG_ORES_ONLY);
        boolean toolRequiredOnly = config.getBoolean(HarvestabilityIdentifiers.CONFIG_TOOL_REQUIRED_ONLY);

        if (showHarvestLevel || showEffectiveTool || showCurrentlyHarvestable) {
            if (showOresOnly && !OreHelper.isBlockAnOre(block, meta)) {
                return;
            }

            if (!player.isCurrentToolAdventureModeExempt(position.blockX, position.blockY, position.blockZ)
                    || BlockHelper.isBlockUnbreakable(
                            block,
                            player.worldObj,
                            position.blockX,
                            position.blockY,
                            position.blockZ)) {
                String unbreakableString = ColorHelper.getBooleanColor(false)
                        + config.getString(HarvestabilityIdentifiers.CONFIG_LEGACY_NOT_CURRENTLY_HARVESTABLE_STRING)
                        + (!minimalLayout
                                ? EnumChatFormatting.RESET
                                        + StatCollector.translateToLocal("wailaharvestability.harvestable")
                                : "");
                stringList.add(unbreakableString);
                return;
            }

            // needed to stop array index out of bounds exceptions on mob spawners
            // block.getHarvestLevel/getHarvestTool are only 16 elements big
            if (meta >= 16) meta = 0;

            int harvestLevel = block.getHarvestLevel(meta);
            String effectiveTool = BlockHelper.getEffectiveToolOf(
                    player.worldObj,
                    position.blockX,
                    position.blockY,
                    position.blockZ,
                    block,
                    meta);
            if (effectiveTool != null && harvestLevel < 0) harvestLevel = 0;
            boolean blockHasEffectiveTools = harvestLevel >= 0 && effectiveTool != null;

            String shearability = BlockHelper.getShearabilityString(player, block, meta, position, config);
            String silkTouchability = BlockHelper.getSilkTouchabilityString(player, block, meta, position, config);

            if (toolRequiredOnly && block.getMaterial().isToolNotRequired()
                    && !blockHasEffectiveTools
                    && shearability.isEmpty()
                    && silkTouchability.isEmpty())
                return;

            boolean canHarvest = false;
            boolean isEffective = false;
            boolean isAboveMinHarvestLevel = false;
            boolean isHoldingTinkersTool = false;
            boolean isHoldingGTTool = false;

            ItemStack itemHeld = player.getHeldItem();
            if (itemHeld != null) {
                isHoldingTinkersTool = ProxyTinkersConstruct.hasToolTag(itemHeld);
                isHoldingGTTool = ProxyGregTech.isGTTool(itemHeld);
                isAboveMinHarvestLevel = (showCurrentlyHarvestable || showHarvestLevel)
                        && ProxyTinkersConstruct.canToolHarvestLevel(itemHeld, block, meta, harvestLevel);
                isEffective = showEffectiveTool
                        && ProxyTinkersConstruct.isToolEffectiveAgainst(itemHeld, block, meta, effectiveTool);
                if (isHoldingGTTool) {
                    // GT tool don't care net.minecraft.block.material.Material#isToolNotRequired
                    canHarvest = itemHeld.func_150998_b(block);
                } else {
                    canHarvest = ToolHelper.canToolHarvestBlock(itemHeld, block, meta)
                            || (!isHoldingTinkersTool && block.canHarvestBlock(player, meta));
                }
            }

            boolean isCurrentlyHarvestable = (canHarvest && isAboveMinHarvestLevel)
                    || (!isHoldingTinkersTool && ForgeHooks.canHarvestBlock(block, player, meta));

            if (hideWhileHarvestable && isCurrentlyHarvestable) return;

            String currentlyHarvestable = showCurrentlyHarvestable
                    ? ColorHelper.getBooleanColor(isCurrentlyHarvestable) + (isCurrentlyHarvestable
                            ? config.getString(HarvestabilityIdentifiers.CONFIG_LEGACY_CURRENTLY_HARVESTABLE_STRING)
                            : config.getString(
                                    HarvestabilityIdentifiers.CONFIG_LEGACY_NOT_CURRENTLY_HARVESTABLE_STRING))
                            + (!minimalLayout
                                    ? EnumChatFormatting.RESET
                                            + StatCollector.translateToLocal("wailaharvestability.currentlyharvestable")
                                    : "")
                    : "";

            if (!currentlyHarvestable.isEmpty() || !shearability.isEmpty() || !silkTouchability.isEmpty()) {
                String separator = (!shearability.isEmpty() || !silkTouchability.isEmpty() ? " " : "");
                stringList.add(
                        currentlyHarvestable + separator
                                + silkTouchability
                                + (!silkTouchability.isEmpty() ? separator : "")
                                + shearability);
            }
            if (harvestLevel != -1 && showEffectiveTool && effectiveTool != null) {
                String effectiveToolString;
                if (StatCollector.canTranslate("wailaharvestability.toolclass." + effectiveTool))
                    effectiveToolString = StatCollector
                            .translateToLocal("wailaharvestability.toolclass." + effectiveTool);
                else effectiveToolString = effectiveTool.substring(0, 1).toUpperCase() + effectiveTool.substring(1);
                stringList.add(
                        (!minimalLayout ? StatCollector.translateToLocal("wailaharvestability.effectivetool") : "")
                                + ColorHelper.getBooleanColor(
                                        isEffective && (!isHoldingTinkersTool || canHarvest),
                                        isHoldingTinkersTool && isEffective && !canHarvest)
                                + effectiveToolString);
            }
            if (harvestLevel >= 1 && (showHarvestLevel || showHarvestLevelNum)) {
                String harvestLevelString = "";
                String harvestLevelName = StringHelper.stripFormatting(StringHelper.getHarvestLevelName(harvestLevel));
                String harvestLevelNum = String.valueOf(harvestLevel);

                // only show harvest level number and name if they are different
                showHarvestLevelNum = showHarvestLevelNum
                        && (!showHarvestLevel || !harvestLevelName.equals(harvestLevelNum));

                if (showHarvestLevel) harvestLevelString = harvestLevelName
                        + (showHarvestLevelNum ? String.format(" (%d)", harvestLevel) : "");
                else if (showHarvestLevelNum) harvestLevelString = harvestLevelNum;

                stringList.add(
                        (!minimalLayout ? StatCollector.translateToLocal("wailaharvestability.harvestlevel") : "") + " "
                                + ColorHelper.getBooleanColor(isAboveMinHarvestLevel && canHarvest)
                                + harvestLevelString);
            }
        }
    }
}

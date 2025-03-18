package com.gtnewhorizons.wdmla.addon.harvestability;

import java.util.ArrayList;
import java.util.List;

import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeHooks;

import com.gtnewhorizons.wdmla.addon.harvestability.helpers.BlockHelper;
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
import org.apache.commons.lang3.StringUtils;

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

        List<IComponent> strings = getLegacyHarvestability(
                accessor.getPlayer(),
                effectiveBlock,
                effectiveMeta,
                accessor.getHitResult(),
                config);
        updateTooltip(strings, tooltip, config);
    }

    private void updateTooltip(List<IComponent> stringParts, ITooltip tooltip, IPluginConfig config) {
        boolean minimalLayout = config.getBoolean(HarvestabilityIdentifiers.CONFIG_MINIMAL);
        String separator = config.getString(HarvestabilityIdentifiers.CONFIG_MINIMAL_SEPARATOR_STRING);
        if (!stringParts.isEmpty()) {
            if (minimalLayout)
                tooltip.child(StringHelper.concatenateStringList(stringParts, EnumChatFormatting.RESET + separator));
            else for (IComponent stringPart : stringParts) {
                tooltip.child(stringPart);
            }
        }
    }

    public List<IComponent> getLegacyHarvestability(EntityPlayer player, Block block, int meta,
            MovingObjectPosition position, IPluginConfig config) {
        List<IComponent> components = new ArrayList<>();
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
                return components;
            }

            if (!player.isCurrentToolAdventureModeExempt(position.blockX, position.blockY, position.blockZ)
                    || BlockHelper.isBlockUnbreakable(
                            block,
                            player.worldObj,
                            position.blockX,
                            position.blockY,
                            position.blockZ)) {
                ITooltip unBreakablePanel = new HPanelComponent();
                unBreakablePanel.child(ThemeHelper.INSTANCE.failure(config.getString(HarvestabilityIdentifiers.CONFIG_LEGACY_NOT_CURRENTLY_HARVESTABLE_STRING)));
                if(!minimalLayout) {
                    unBreakablePanel.text(LangUtil.translateG("wailaharvestability.harvestable"));
                }
                components.add(unBreakablePanel);
                return components;
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

            IComponent shearability = BlockHelper.getShearabilityString(player, block, meta, position, config);
            IComponent silkTouchability = BlockHelper.getSilkTouchabilityString(player, block, meta, position, config);

            if (toolRequiredOnly && block.getMaterial().isToolNotRequired()
                    && !blockHasEffectiveTools
                    && shearability == null
                    && silkTouchability == null)
                return components;

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

            if (hideWhileHarvestable && isCurrentlyHarvestable) return components;

            ITooltip currentlyHarvestable = null;
            if (showCurrentlyHarvestable) {
                currentlyHarvestable = new HPanelComponent();
                if(isCurrentlyHarvestable) {
                    String icon = config.getString(HarvestabilityIdentifiers.CONFIG_LEGACY_CURRENTLY_HARVESTABLE_STRING);
                    currentlyHarvestable.child(ThemeHelper.INSTANCE.success(icon));
                }
                else {
                    String icon = config.getString(HarvestabilityIdentifiers.CONFIG_LEGACY_NOT_CURRENTLY_HARVESTABLE_STRING);
                    currentlyHarvestable.child(ThemeHelper.INSTANCE.failure(icon));
                }
                if (!minimalLayout) {
                    String suffix = LangUtil.translateG("wailaharvestability.currentlyharvestable");
                    currentlyHarvestable.text(suffix);
                }
            }

            if (currentlyHarvestable != null || shearability != null || silkTouchability == null) {
                String separator = StringUtils.SPACE;
                if (silkTouchability != null) {
                    currentlyHarvestable.text(separator);
                    currentlyHarvestable.child(silkTouchability);
                }
                if (shearability != null) {
                    currentlyHarvestable.text(separator);
                    currentlyHarvestable.child(shearability);
                }
                components.add(currentlyHarvestable);
            }
            if (harvestLevel != -1 && showEffectiveTool && effectiveTool != null) {
                String effectiveToolString;
                if (StatCollector.canTranslate("wailaharvestability.toolclass." + effectiveTool))
                    effectiveToolString = StatCollector
                            .translateToLocal("wailaharvestability.toolclass." + effectiveTool);
                else effectiveToolString = effectiveTool.substring(0, 1).toUpperCase() + effectiveTool.substring(1);
                ITooltip effectiveToolPanel = new HPanelComponent();
                if(!minimalLayout) {
                    effectiveToolPanel.child(new TextComponent(LangUtil.translateG("wailaharvestability.effectivetool") + ": "));
                }
                if(isEffective && (!isHoldingTinkersTool || canHarvest)) {
                    effectiveToolPanel.child(ThemeHelper.INSTANCE.success(effectiveToolString));
                }
                else {
                    effectiveToolPanel.child(ThemeHelper.INSTANCE.failure(effectiveToolString));
                }
                components.add(effectiveToolPanel);
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

                ITooltip harvestText = new HPanelComponent();
                if(!minimalLayout) {
                    harvestText.text(StatCollector.translateToLocal("wailaharvestability.harvestlevel") + ": ");
                }
                if(isAboveMinHarvestLevel && canHarvest) {
                    harvestText.child(ThemeHelper.INSTANCE.success(harvestLevelString));
                }
                else {
                    harvestText.child(ThemeHelper.INSTANCE.failure(harvestLevelString));
                }
                components.add(harvestText);
            }
        }

        return components;
    }
}

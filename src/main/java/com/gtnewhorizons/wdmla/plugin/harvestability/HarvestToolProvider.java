package com.gtnewhorizons.wdmla.plugin.harvestability;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.gtnewhorizons.wdmla.plugin.PluginsConfig;
import com.gtnewhorizons.wdmla.config.General;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.gtnewhorizons.wdmla.plugin.harvestability.helpers.BlockHelper;
import com.gtnewhorizons.wdmla.plugin.harvestability.helpers.StringHelper;
import com.gtnewhorizons.wdmla.plugin.harvestability.helpers.ToolHelper;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyCreativeBlocks;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.plugin.harvestability.proxy.ProxyTinkersConstruct;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;

import cpw.mods.fml.common.registry.GameRegistry;
import mcp.mobius.waila.cbcore.LangUtil;

public enum HarvestToolProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public ResourceLocation getUid() {
        return HarvestabilityIdentifiers.HARVESTABILITY;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HARVESTABILITY_OVERRIDE;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        if (General.forceLegacy) {
            return;
        }

        if (ProxyCreativeBlocks.isCreativeBlock(accessor.getBlock(), accessor.getMetadata())) {
            return;
        }

        Block effectiveBlock = BlockHelper
                .getEffectiveBlock(accessor.getBlock(), accessor.getItemForm(), accessor.getMetadata());
        int effectiveMeta = BlockHelper
                .getEffectiveMeta(accessor.getBlock(), accessor.getItemForm(), accessor.getMetadata());

        List<IComponent> harvestableDisplay = getHarvestability(
                accessor.getPlayer(),
                effectiveBlock,
                effectiveMeta,
                accessor.getHitResult());

        updateTooltip(tooltip, harvestableDisplay);
    }

    private void updateTooltip(ITooltip tooltip, List<IComponent> harvestableDisplay) {
        IComponent itemNameRow = tooltip.getChildWithTag(Identifiers.ITEM_NAME_ROW);
        if (!(itemNameRow instanceof ITooltip)) {
            return;
        }

        ((ITooltip) itemNameRow).child(harvestableDisplay.get(0));
        if (harvestableDisplay.size() > 1 && harvestableDisplay.get(1) != null
                && PluginsConfig.harvestability.modern.modernHarvestLevelNum) {
            tooltip.child(harvestableDisplay.get(1));
        }
    }

    /**
     * @return element1: harvest tool icon to append after item name
     *         <p>
     *         element2: harvestability String if the harvest level is greater than 0
     */
    public List<IComponent> getHarvestability(EntityPlayer player, Block block, int meta, MovingObjectPosition position) {
        if (!player.isCurrentToolAdventureModeExempt(position.blockX, position.blockY, position.blockZ) || BlockHelper
                .isBlockUnbreakable(block, player.worldObj, position.blockX, position.blockY, position.blockZ)) {
            return Arrays.asList(
                    ThemeHelper.INSTANCE.failure(PluginsConfig.harvestability.modern.notCurrentlyHarvestableString));
        }

        // needed to stop array index out of bounds exceptions on mob spawners
        // block.getHarvestLevel/getHarvestTool are only 16 elements big
        if (meta >= 16) meta = 0;

        String effectiveTool = BlockHelper
                .getEffectiveToolOf(player.worldObj, position.blockX, position.blockY, position.blockZ, block, meta);
        int harvestLevel = getHarvestLevel(block, meta, effectiveTool);

        IComponent shearability = BlockHelper.getShearabilityString(player, block, meta, position);
        IComponent silkTouchability = BlockHelper.getSilkTouchabilityString(player, block, meta, position);

        if (canInstaBreak(harvestLevel, effectiveTool, block, shearability != null, silkTouchability != null)) {
            return Arrays
                    .asList(ThemeHelper.INSTANCE.success(PluginsConfig.harvestability.modern.currentlyHarvestableString));
        }

        ItemStack itemHeld = player.getHeldItem();

        ITooltip effectiveToolIconComponent = getEffectiveToolIcon(harvestLevel, effectiveTool);

        boolean isCurrentlyHarvestable = isCurrentlyHarvestable(
                player,
                block,
                meta,
                itemHeld,
                effectiveTool,
                harvestLevel);

        ITooltip harvestabilityIcon = assembleHarvestabilityIcon(
                effectiveToolIconComponent,
                isCurrentlyHarvestable,
                shearability,
                silkTouchability);
        IComponent harvestLevelText = assembleHarvestLevelText(harvestLevel, isCurrentlyHarvestable);
        return Arrays.asList(harvestabilityIcon, harvestLevelText);
    }

    private static int getHarvestLevel(Block block, int meta, String effectiveTool) {
        int harvestLevel = block.getHarvestLevel(meta);
        if (effectiveTool != null && harvestLevel < 0) harvestLevel = 0;
        return harvestLevel;
    }

    private static boolean canInstaBreak(int harvestLevel, String effectiveTool, Block block, boolean canShear,
            boolean canSilkTouch) {
        boolean blockHasEffectiveTools = harvestLevel >= 0 && effectiveTool != null;
        return block.getMaterial().isToolNotRequired() && !blockHasEffectiveTools && !canShear && !canSilkTouch;
    }

    private static boolean isHoldingTinkersTool(ItemStack itemHeld) {
        return itemHeld != null && ProxyTinkersConstruct.hasToolTag(itemHeld);
    }

    private static boolean isAboveMinHarvestLevel(ItemStack itemHeld, Block block, int meta, int harvestLevel) {
        return itemHeld != null && ProxyTinkersConstruct.canToolHarvestLevel(itemHeld, block, meta, harvestLevel);
    }

    private static boolean isHeldToolCorrect(EntityPlayer player, Block block, int meta, ItemStack itemHeld,
            String effectiveTool, boolean isHoldingTinkersTool) {
        boolean canHarvest = false;
        if (itemHeld != null) {
            if (ProxyGregTech.isMachine(block)) {
                // GT_MetaGenerated_Tool's getDigSpeed is broken
                canHarvest = Objects.equals(effectiveTool, ProxyGregTech.TOOL_WRENCH)
                        && ProxyGregTech.isWrench(itemHeld)
                        || Objects.equals(effectiveTool, ProxyGregTech.TOOL_WIRE_CUTTER)
                                && ProxyGregTech.isWireCutter(itemHeld);
            } else if (ProxyGregTech.isGTTool(itemHeld)) {
                // GT tool don't care net.minecraft.block.material.Material#isToolNotRequired
                canHarvest = itemHeld.func_150998_b(block);
            } else {
                canHarvest = ToolHelper.canToolHarvestBlock(itemHeld, block, meta)
                        || (!isHoldingTinkersTool && block.canHarvestBlock(player, meta));
            }
        }
        return canHarvest;
    }

    private static @Nullable ITooltip getEffectiveToolIcon(int harvestLevel, String effectiveTool) {
        ITooltip effectiveToolIconComponent = null;
        if (harvestLevel != -1 && effectiveTool != null) {
            ItemStack effectiveToolIcon = ToolHelper.getEffectiveToolIcon(effectiveTool, harvestLevel);
            // remove durability bar from tool icon
            effectiveToolIconComponent = new ItemComponent(effectiveToolIcon).doDrawOverlay(false)
                    .size(new Size(10, 10));
        }
        return effectiveToolIconComponent;
    }

    private static boolean isCurrentlyHarvestable(EntityPlayer player, Block block, int meta, ItemStack itemHeld,
            String effectiveTool, int harvestLevel) {
        boolean isHoldingTinkersTool = isHoldingTinkersTool(itemHeld);
        boolean isHeldToolCorrect = isHeldToolCorrect(
                player,
                block,
                meta,
                itemHeld,
                effectiveTool,
                isHoldingTinkersTool);
        boolean isAboveMinHarvestLevel = isAboveMinHarvestLevel(itemHeld, block, meta, harvestLevel);
        return (isHeldToolCorrect && isAboveMinHarvestLevel)
                || (!ProxyGregTech.isMachine(block) && !isHoldingTinkersTool
                        && ForgeHooks.canHarvestBlock(block, player, meta));
    }

    private static @NotNull ITooltip assembleHarvestabilityIcon(ITooltip effectiveToolIconComponent,
            boolean isCurrentlyHarvestable, IComponent shearability, IComponent silkTouchability) {
        ITooltip harvestabilityComponent = new HPanelComponent().tag(HarvestabilityIdentifiers.HARVESTABILITY_ICON);
        // TODO: resize CHECK text
        IComponent currentlyHarvestableIcon = (isCurrentlyHarvestable
                ? ThemeHelper.INSTANCE.success(PluginsConfig.harvestability.modern.currentlyHarvestableString)
                : ThemeHelper.INSTANCE.failure(PluginsConfig.harvestability.modern.notCurrentlyHarvestableString));

        if (PluginsConfig.harvestability.modern.modernCurrentlyHarvestableIcon) {
            if (effectiveToolIconComponent != null && PluginsConfig.harvestability.modern.modernEffectiveToolIcon) {
                effectiveToolIconComponent.child(
                        new HPanelComponent().padding(new Padding().left(5).top(6)).child(currentlyHarvestableIcon));
                harvestabilityComponent.child(effectiveToolIconComponent);
            } else {
                harvestabilityComponent.child(currentlyHarvestableIcon);
            }
        }
        if (shearability != null && PluginsConfig.harvestability.modern.modernShowShearabilityIcon) {
            String[] parts = PluginsConfig.harvestability.modern.shearabilityItem.split(":");
            if (parts.length == 2) {
                harvestabilityComponent.child(
                        new ItemComponent(GameRegistry.findItemStack(parts[0], parts[1], 1)).doDrawOverlay(false)
                                .size(new Size(10, 10)));
            }
        }
        if (silkTouchability != null && PluginsConfig.harvestability.modern.modernShowSilkTouchabilityIcon) {
            String[] parts = PluginsConfig.harvestability.modern.silkTouchabilityItem.split(":");
            if (parts.length == 2) {
                harvestabilityComponent.child(
                        new ItemComponent(GameRegistry.findItemStack(parts[0], parts[1], 1)).doDrawOverlay(false)
                                .size(new Size(10, 10)));
            }
        }
        return harvestabilityComponent;
    }

    private static @Nullable IComponent assembleHarvestLevelText(int harvestLevel, boolean isCurrentlyHarvestable) {
        IComponent harvestLevelText = null;
        if (harvestLevel >= 1) {
            String harvestLevelString = StringHelper.stripFormatting(StringHelper.getHarvestLevelName(harvestLevel));
            harvestLevelText = new HPanelComponent().tag(HarvestabilityIdentifiers.HARVESTABILITY_TEXT)
                    .text(String.format("%s: ", LangUtil.translateG("hud.msg.wdmla.harvestlevel"))).child(
                            isCurrentlyHarvestable ? ThemeHelper.INSTANCE.success(harvestLevelString)
                                    : ThemeHelper.INSTANCE.failure(harvestLevelString));
        }
        return harvestLevelText;
    }
}

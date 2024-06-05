package com.gtnewhorizons.wdmla.addon.harvestability;

import static com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityConstant.SHEARABILITY_ICON;
import static com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityConstant.SILKTOUCH_ICON;
import static mcp.mobius.waila.api.SpecialChars.WHITE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.gtnewhorizons.wdmla.impl.ui.style.ItemStyle;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;

import com.gtnewhorizons.wdmla.addon.harvestability.helpers.*;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyCreativeBlocks;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.DisplayUtil;

public class HarvestToolProvider implements IComponentProvider<BlockAccessor> {

    @Override
    public ResourceLocation getUid() {
        return Identifiers.HARVESTABILITY;
    }

    @Override
    public int getDefaultPriority() {
        return -8000;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        Block block = accessor.getBlock();
        int meta = accessor.getMetadata();

        if (ProxyCreativeBlocks.isCreativeBlock(block, meta)) return;

        EntityPlayer player = accessor.getPlayer();

        // for disguised blocks
        if (accessor.getItemForm().getItem() instanceof ItemBlock && !ProxyGregTech.isOreBlock(block)
                && !ProxyGregTech.isCasing(block)
                && !ProxyGregTech.isMachine(block)) {
            block = Block.getBlockFromItem(accessor.getItemForm().getItem());
            meta = accessor.getItemForm().getItemDamage();
        }

        boolean forceLegacyMode = ConfigHandler.instance().getConfig("harvestability.forceLegacyMode", false);

        if (!forceLegacyMode) {
            List<IComponent> harvestableDisplay = getHarvestability(
                    player,
                    block,
                    meta,
                    accessor.getHitResult(),
                    ConfigHandler.instance());
            IComponent replacedName = new HPanelComponent()
                    .text(WHITE + DisplayUtil.itemDisplayNameShort(accessor.getItemForm())).child(harvestableDisplay.get(0))
                    .tag(Identifiers.ITEM_NAME);
            if (!tooltip.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName)) {
                throw new RuntimeException("WDMla Harvestability Module couldn't find item name in the tooltip");
            }
            if(harvestableDisplay.size() > 1 && harvestableDisplay.get(1) != null) {
                tooltip.child(harvestableDisplay.get(1));
            }
        } else {
            boolean minimalLayout = ConfigHandler.instance().getConfig("harvestability.minimal", false);
            List<String> stringParts = new ArrayList<>();
            getLegacyHarvestability(
                    stringParts,
                    player,
                    block,
                    meta,
                    accessor.getHitResult(),
                    ConfigHandler.instance(),
                    minimalLayout);
            if (!stringParts.isEmpty()) {
                if (minimalLayout) tooltip.text(
                        StringHelper.concatenateStringList(
                                stringParts,
                                EnumChatFormatting.RESET + HarvestabilityConstant.MINIMAL_SEPARATOR_STRING));
                else for (String stringPart : stringParts) {
                    tooltip.text(stringPart);
                }
            }
        }
    }

    /**
     *
     * @return element1: harvest tool icon to append after item name
     * <p>
     * element2: harvestability String if the harvest level is greater than 0
     */
    public List<IComponent> getHarvestability(EntityPlayer player, Block block, int meta, MovingObjectPosition position,
            IWailaConfigHandler config) {
        if (!player.isCurrentToolAdventureModeExempt(position.blockX, position.blockY, position.blockZ) || BlockHelper
                .isBlockUnbreakable(block, player.worldObj, position.blockX, position.blockY, position.blockZ)) {
            return Arrays.asList(new TextComponent(ColorHelper.getBooleanColor(false) + HarvestabilityConstant.X));
        }

        // needed to stop array index out of bounds exceptions on mob spawners
        // block.getHarvestLevel/getHarvestTool are only 16 elements big
        if (meta >= 16) meta = 0;

        int harvestLevel = block.getHarvestLevel(meta);
        String effectiveTool = BlockHelper
                .getEffectiveToolOf(player.worldObj, position.blockX, position.blockY, position.blockZ, block, meta);
        if (effectiveTool != null && harvestLevel < 0) harvestLevel = 0;
        boolean blockHasEffectiveTools = harvestLevel >= 0 && effectiveTool != null;

        String shearability = getShearabilityString(player, block, meta, position, config);
        String silkTouchability = getSilkTouchabilityString(player, block, meta, position, config);

        if (block.getMaterial().isToolNotRequired() && !blockHasEffectiveTools
                && shearability.isEmpty()
                && silkTouchability.isEmpty())
            return Arrays.asList(new TextComponent(ColorHelper.getBooleanColor(true) + HarvestabilityConstant.CHECK));

        ITooltip harvestabilityComponent = new HPanelComponent();

        boolean canHarvest = false;
        boolean isAboveMinHarvestLevel = false;
        boolean isHoldingTinkersTool = false;
        boolean isHoldingGTTool;

        ItemStack itemHeld = player.getHeldItem();
        if (itemHeld != null) {
            isHoldingTinkersTool = ToolHelper.hasToolTag(itemHeld);
            isHoldingGTTool = ProxyGregTech.isGTTool(itemHeld);
            isAboveMinHarvestLevel = ToolHelper.canToolHarvestLevel(itemHeld, block, meta, harvestLevel);
            if (isHoldingGTTool) {
                // GT tool don't care net.minecraft.block.material.Material#isToolNotRequired
                canHarvest = itemHeld.func_150998_b(block);
            } else {
                canHarvest = ToolHelper.canToolHarvestBlock(itemHeld, block, meta)
                        || (!isHoldingTinkersTool && block.canHarvestBlock(player, meta));
            }
        }

        ITooltip effectiveToolIconComponent = null;
        if (harvestLevel != -1 && effectiveTool != null) {
            ItemStack effectiveToolIcon;
            effectiveToolIcon = ToolHelper.getEffectiveToolIcon(effectiveTool, harvestLevel);
            effectiveToolIconComponent = new ItemComponent(effectiveToolIcon)
                    .style(new ItemStyle().drawOverlay(false)) //we don't want durability bar on tool icon
                    .size(new Size(10, 10));
            harvestabilityComponent.child(effectiveToolIconComponent);
        }

        boolean isCurrentlyHarvestable = (canHarvest && isAboveMinHarvestLevel)
                || (!isHoldingTinkersTool && ForgeHooks.canHarvestBlock(block, player, meta));

        //TODO: resize CHECK text
        String currentlyHarvestable = ColorHelper.getBooleanColor(isCurrentlyHarvestable)
                + (isCurrentlyHarvestable ? HarvestabilityConstant.CHECK : HarvestabilityConstant.X);

        if (effectiveToolIconComponent != null) {
            effectiveToolIconComponent.text(currentlyHarvestable, new TextStyle(), new Padding().left(5).top(6));
        } else {
            harvestabilityComponent.text(currentlyHarvestable);
        }
        if (!shearability.isEmpty()) {
            harvestabilityComponent.item(SHEARABILITY_ICON, new Padding(), new Size(10, 10));
        }
        if (!silkTouchability.isEmpty()) {
            harvestabilityComponent.item(SILKTOUCH_ICON, new Padding(), new Size(10, 10));
        }

        IComponent harvestLevelText = null;
        if (harvestLevel >= 1) {
            String harvestLevelString = "";

            harvestLevelString = StringHelper.stripFormatting(StringHelper.getHarvestLevelName(harvestLevel));

            harvestLevelText = new TextComponent(StatCollector.translateToLocal("wailaharvestability.harvestlevel")
                    + ColorHelper.getBooleanColor(isAboveMinHarvestLevel && canHarvest)
                    + harvestLevelString);
        }

        return Arrays.asList(harvestabilityComponent, harvestLevelText);
    }

    public void getLegacyHarvestability(List<String> stringList, EntityPlayer player, Block block, int meta,
            MovingObjectPosition position, IWailaConfigHandler config, boolean minimalLayout) {
        boolean isSneaking = player.isSneaking();
        boolean showHarvestLevel = config.getConfig("harvestability.harvestlevel")
                && (!config.getConfig("harvestability.harvestlevel.sneakingonly") || isSneaking);
        boolean showHarvestLevelNum = config.getConfig("harvestability.harvestlevelnum")
                && (!config.getConfig("harvestability.harvestlevelnum.sneakingonly") || isSneaking);
        boolean showEffectiveTool = config.getConfig("harvestability.effectivetool")
                && (!config.getConfig("harvestability.effectivetool.sneakingonly") || isSneaking);
        boolean showCurrentlyHarvestable = config.getConfig("harvestability.currentlyharvestable")
                && (!config.getConfig("harvestability.currentlyharvestable.sneakingonly") || isSneaking);
        boolean hideWhileHarvestable = config.getConfig("harvestability.unharvestableonly", false);
        boolean showOresOnly = config.getConfig("harvestability.oresonly", false);
        boolean toolRequiredOnly = config.getConfig("harvestability.toolrequiredonly");

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
                        + HarvestabilityConstant.NOT_CURRENTLY_HARVESTABLE_STRING
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

            String shearability = getShearabilityString(player, block, meta, position, config);
            String silkTouchability = getSilkTouchabilityString(player, block, meta, position, config);

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
                isHoldingTinkersTool = ToolHelper.hasToolTag(itemHeld);
                isHoldingGTTool = ProxyGregTech.isGTTool(itemHeld);
                isAboveMinHarvestLevel = (showCurrentlyHarvestable || showHarvestLevel)
                        && ToolHelper.canToolHarvestLevel(itemHeld, block, meta, harvestLevel);
                isEffective = showEffectiveTool
                        && ToolHelper.isToolEffectiveAgainst(itemHeld, block, meta, effectiveTool);
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
                    ? ColorHelper.getBooleanColor(isCurrentlyHarvestable)
                            + (isCurrentlyHarvestable ? HarvestabilityConstant.CURRENTLY_HARVESTABLE_STRING
                                    : HarvestabilityConstant.NOT_CURRENTLY_HARVESTABLE_STRING)
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
                        (!minimalLayout ? StatCollector.translateToLocal("wailaharvestability.harvestlevel") : "")
                                + ColorHelper.getBooleanColor(isAboveMinHarvestLevel && canHarvest)
                                + harvestLevelString);
            }
        }
    }

    public String getShearabilityString(EntityPlayer player, Block block, int meta, MovingObjectPosition position,
            IWailaConfigHandler config) {
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
                    + HarvestabilityConstant.SHEARABILITY_STRING;
        }
        return "";
    }

    public String getSilkTouchabilityString(EntityPlayer player, Block block, int meta, MovingObjectPosition position,
            IWailaConfigHandler config) {
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
                return ColorHelper.getBooleanColor(hasSilkTouch) + HarvestabilityConstant.SILK_TOUCHABILITY_STRING;
            }
        }
        return "";
    }
}

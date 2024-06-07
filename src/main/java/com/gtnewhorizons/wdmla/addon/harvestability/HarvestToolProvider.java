package com.gtnewhorizons.wdmla.addon.harvestability;

import static com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityIdentifiers.SHEARABILITY_ICON;
import static com.gtnewhorizons.wdmla.addon.harvestability.HarvestabilityIdentifiers.SILKTOUCH_ICON;
import static mcp.mobius.waila.api.SpecialChars.WHITE;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyTinkersConstruct;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeHooks;

import com.gtnewhorizons.wdmla.addon.harvestability.helpers.BlockHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.helpers.ColorHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.helpers.StringHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.helpers.ToolHelper;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyCreativeBlocks;
import com.gtnewhorizons.wdmla.addon.harvestability.proxy.ProxyGregTech;
import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.ItemComponent;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Padding;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Size;
import com.gtnewhorizons.wdmla.impl.ui.style.ItemStyle;
import com.gtnewhorizons.wdmla.impl.ui.style.TextStyle;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.impl.ConfigHandler;
import mcp.mobius.waila.overlay.DisplayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HarvestToolProvider implements IComponentProvider<BlockAccessor> {

    @Override
    public ResourceLocation getUid() {
        return Identifiers.HARVESTABILITY;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HARVESTABILITY_OVERRIDE;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        boolean forceLegacyMode = ConfigHandler.instance().getConfig("harvestability.forceLegacyMode", false);
        if (forceLegacyMode) {
            return;
        }

        if (ProxyCreativeBlocks.isCreativeBlock(accessor.getBlock(), accessor.getMetadata())){
            return;
        }

        Block effectiveBlock = BlockHelper.getEffectiveBlock(accessor.getBlock(), accessor.getItemForm());
        int effectiveMeta = BlockHelper.getEffectiveMeta(accessor.getBlock(), accessor.getItemForm(), accessor.getMetadata());

        List<IComponent> harvestableDisplay = getHarvestability(
                accessor.getPlayer(),
                effectiveBlock,
                effectiveMeta,
                accessor.getHitResult(),
                ConfigHandler.instance());

        updateTooltip(tooltip, accessor, harvestableDisplay);
    }

    private void updateTooltip(ITooltip tooltip, BlockAccessor accessor, List<IComponent> harvestableDisplay) {
        IComponent replacedName = new HPanelComponent()
                .text(WHITE + DisplayUtil.itemDisplayNameShort(accessor.getItemForm())).child(harvestableDisplay.get(0))
                .tag(Identifiers.ITEM_NAME);
        if (!tooltip.replaceChildWithTag(Identifiers.ITEM_NAME, replacedName)) {
            throw new RuntimeException("WDMla Harvestability Module couldn't find item name in the tooltip");
        }
        if (harvestableDisplay.size() > 1 && harvestableDisplay.get(1) != null) {
            tooltip.child(harvestableDisplay.get(1));
        }
    }

    /**
     *
     * @return element1: harvest tool icon to append after item name
     *         <p>
     *         element2: harvestability String if the harvest level is greater than 0
     */
    public List<IComponent> getHarvestability(EntityPlayer player, Block block, int meta, MovingObjectPosition position,
            IWailaConfigHandler config) {
        if (!player.isCurrentToolAdventureModeExempt(position.blockX, position.blockY, position.blockZ) || BlockHelper
                .isBlockUnbreakable(block, player.worldObj, position.blockX, position.blockY, position.blockZ)) {
            return Arrays.asList(new TextComponent(ColorHelper.getBooleanColor(false) + HarvestabilityIdentifiers.X));
        }

        // needed to stop array index out of bounds exceptions on mob spawners
        // block.getHarvestLevel/getHarvestTool are only 16 elements big
        if (meta >= 16) meta = 0;

        String effectiveTool = BlockHelper
                .getEffectiveToolOf(player.worldObj, position.blockX, position.blockY, position.blockZ, block, meta);
        int harvestLevel = getHarvestLevel(block, meta, effectiveTool);

        String shearability = BlockHelper.getShearabilityString(player, block, meta, position, config);
        String silkTouchability = BlockHelper.getSilkTouchabilityString(player, block, meta, position, config);

        if (canInstaBreak(harvestLevel, effectiveTool, block, !shearability.isEmpty(), !silkTouchability.isEmpty())) {
            return Arrays
                    .asList(new TextComponent(ColorHelper.getBooleanColor(true) + HarvestabilityIdentifiers.CHECK));
        }

        ItemStack itemHeld = player.getHeldItem();

        ITooltip effectiveToolIconComponent = getEffectiveToolIcon(harvestLevel, effectiveTool);

        boolean isCurrentlyHarvestable = isCurrentlyHarvestable(player, block, meta, itemHeld, effectiveTool, harvestLevel);

        ITooltip harvestabilityIcon = assembleHarvestabilityIcon(effectiveToolIconComponent, isCurrentlyHarvestable, shearability, silkTouchability);
        IComponent harvestLevelText = assembleHarvestLevelText(harvestLevel, isCurrentlyHarvestable);
        return Arrays.asList(harvestabilityIcon, harvestLevelText);
    }

    private static int getHarvestLevel(Block block, int meta, String effectiveTool) {
        int harvestLevel = block.getHarvestLevel(meta);
        if (effectiveTool != null && harvestLevel < 0) harvestLevel = 0;
        return harvestLevel;
    }

    private static boolean canInstaBreak(int harvestLevel, String effectiveTool, Block block, boolean canShear, boolean canSilkTouch) {
        boolean blockHasEffectiveTools = harvestLevel >= 0 && effectiveTool != null;
        return block.getMaterial().isToolNotRequired() && !blockHasEffectiveTools
                && !canShear
                && !canSilkTouch;
    }

    private static boolean isHoldingTinkersTool(ItemStack itemHeld) {
        return itemHeld != null && ProxyTinkersConstruct.hasToolTag(itemHeld);
    }

    private static boolean isAboveMinHarvestLevel(ItemStack itemHeld, Block block, int meta, int harvestLevel) {
        return itemHeld != null && ProxyTinkersConstruct.canToolHarvestLevel(itemHeld, block, meta, harvestLevel);
    }

    private static boolean isHeldToolCorrect(EntityPlayer player, Block block, int meta, ItemStack itemHeld, String effectiveTool, boolean isHoldingTinkersTool) {
        boolean canHarvest = false;
        if (itemHeld != null) {
            if (ProxyGregTech.isMachine(block)) {
                // GT_MetaGenerated_Tool's getDigSpeed is broken
                canHarvest = Objects.equals(effectiveTool, "wrench") && ProxyGregTech.isWrench(itemHeld)
                        || Objects.equals(effectiveTool, "cutter") && ProxyGregTech.isWireCutter(itemHeld);
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
            //remove durability bar from tool icon
            effectiveToolIconComponent = new ItemComponent(effectiveToolIcon).style(new ItemStyle().drawOverlay(false))
                    .size(new Size(10, 10));
        }
        return effectiveToolIconComponent;
    }

    private static boolean isCurrentlyHarvestable(EntityPlayer player, Block block, int meta, ItemStack itemHeld,
                                                  String effectiveTool, int harvestLevel) {
        boolean isHoldingTinkersTool = isHoldingTinkersTool(itemHeld);
        boolean isHeldToolCorrect = isHeldToolCorrect(player, block, meta, itemHeld, effectiveTool, isHoldingTinkersTool);
        boolean isAboveMinHarvestLevel = isAboveMinHarvestLevel(itemHeld, block, meta, harvestLevel);
        return (isHeldToolCorrect && isAboveMinHarvestLevel)
                || (!ProxyGregTech.isMachine(block) && !isHoldingTinkersTool
                && ForgeHooks.canHarvestBlock(block, player, meta));
    }

    private static @NotNull ITooltip assembleHarvestabilityIcon(ITooltip effectiveToolIconComponent, boolean isCurrentlyHarvestable, String shearability, String silkTouchability) {
        ITooltip harvestabilityComponent = new HPanelComponent();
        // TODO: resize CHECK text
        String currentlyHarvestableIcon = ColorHelper.getBooleanColor(isCurrentlyHarvestable)
                + (isCurrentlyHarvestable ? HarvestabilityIdentifiers.CHECK : HarvestabilityIdentifiers.X);

        if (effectiveToolIconComponent != null) {
            effectiveToolIconComponent.text(currentlyHarvestableIcon, new TextStyle(), new Padding().left(5).top(6));
            harvestabilityComponent.child(effectiveToolIconComponent);
        } else {
            harvestabilityComponent.text(currentlyHarvestableIcon);
        }
        if (!shearability.isEmpty()) {
            harvestabilityComponent.item(SHEARABILITY_ICON, new Padding(), new Size(10, 10));
        }
        if (!silkTouchability.isEmpty()) {
            harvestabilityComponent.item(SILKTOUCH_ICON, new Padding(), new Size(10, 10));
        }
        return harvestabilityComponent;
    }

    private static @Nullable IComponent assembleHarvestLevelText(int harvestLevel, boolean isCurrentlyHarvestable) {
        IComponent harvestLevelText = null;
        if (harvestLevel >= 1) {
            String harvestLevelString = StringHelper.stripFormatting(StringHelper.getHarvestLevelName(harvestLevel));
            harvestLevelText = new TextComponent(
                    StatCollector.translateToLocal("wailaharvestability.harvestlevel")
                            + ColorHelper.getBooleanColor(isCurrentlyHarvestable)
                            + harvestLevelString);
        }
        return harvestLevelText;
    }
}

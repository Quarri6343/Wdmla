package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public class DroppedItemHeaderProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        ItemStack newStack = new ItemStack(
                accessor.getBlock(),
                1,
                accessor.getBlock().damageDropped(accessor.getMetadata()));
        ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, newStack);
        ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, newStack);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.DROPPED_ITEM_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}

package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public enum CustomMetaDataHeaderProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        Block block = accessor.getBlock();
        ItemStack newItemStack = null;
        if ((block == Blocks.leaves || block == Blocks.leaves2) && (accessor.getMetadata() > 3)) {
            newItemStack = new ItemStack(block, 1, accessor.getMetadata() - 4);
        }

        if (block == Blocks.log || block == Blocks.log2) {
            newItemStack = new ItemStack(block, 1, accessor.getMetadata() % 4);
        }

        if ((block == Blocks.quartz_block) && (accessor.getMetadata() > 2)) {
            newItemStack = new ItemStack(block, 1, 2);
        }

        if (newItemStack != null) {
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, newItemStack);
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, newItemStack);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.CUSTOM_META_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}

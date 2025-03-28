package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public enum SilverFishBlockHeaderProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        int metadata = accessor.getMetadata();
        ItemStack dummyStack = switch (metadata) {
            case 1 -> new ItemStack(Blocks.cobblestone);
            case 2 -> new ItemStack(Blocks.stonebrick, 1, 0);
            case 3 -> new ItemStack(Blocks.stonebrick, 1, 1);
            case 4 -> new ItemStack(Blocks.stonebrick, 1, 2);
            case 5 -> new ItemStack(Blocks.stonebrick, 1, 3);
            default -> new ItemStack(Blocks.stone);
        };
        ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, dummyStack);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.SILVERFISH_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}

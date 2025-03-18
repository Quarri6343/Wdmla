package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public class DoublePlantHeaderProvider implements IBlockComponentProvider {

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if ((accessor.getMetadata() & 8) != 0) {
            int x = accessor.getHitResult().blockX;
            int y = accessor.getHitResult().blockY - 1;
            int z = accessor.getHitResult().blockZ;
            int meta = accessor.getWorld().getBlockMetadata(x, y, z);

            ItemStack newStack = new ItemStack(Blocks.double_plant, 0, meta);
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, newStack);
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, newStack);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.DOUBLE_PLANT_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}

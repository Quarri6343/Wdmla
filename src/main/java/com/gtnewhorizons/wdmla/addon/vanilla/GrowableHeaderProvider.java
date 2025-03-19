package com.gtnewhorizons.wdmla.addon.vanilla;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.IPluginConfig;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;

// includes crops and stems
public enum GrowableHeaderProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getBlock().equals(Blocks.wheat)) {
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, LangUtil.translateG("hud.item.wheatcrop"));
        } else if (accessor.getBlock().equals(Blocks.carrots)) {
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, new ItemStack(Items.carrot));
        } else if (accessor.getBlock().equals(Blocks.potatoes)) {
            ThemeHelper.INSTANCE.overrideTooltipIcon(tooltip, new ItemStack(Items.potato));
        } else if (accessor.getBlock().equals(Blocks.pumpkin_stem)) {
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, LangUtil.translateG("hud.item.pumpkinstem"));
        } else if (accessor.getBlock().equals(Blocks.melon_stem)) {
            ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, LangUtil.translateG("hud.item.melonstem"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.GROWABLE_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}

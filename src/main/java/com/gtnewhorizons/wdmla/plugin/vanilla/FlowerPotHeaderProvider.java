package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.overlay.DisplayUtil;

public enum FlowerPotHeaderProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        String formattedName = String.format(
                StatCollector.translateToLocal("hud.msg.wdmla.flower.pot"),
                DisplayUtil.itemDisplayNameShort(accessor.getItemForm()));
        ThemeHelper.INSTANCE.overrideTooltipTitle(tooltip, formattedName);
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.FLOWER_POT_HEADER;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.HEAD;
    }
}

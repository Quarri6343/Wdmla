package com.gtnewhorizons.wdmla.plugin.core;

import com.gtnewhorizons.wdmla.util.FormatUtil;
import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.util.StatCollector;

public enum HardnessProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        float hardness = accessor.getBlock().getBlockHardness(
                accessor.getWorld(),
                accessor.getHitResult().blockX,
                accessor.getHitResult().blockY,
                accessor.getHitResult().blockZ);
        tooltip.child(
                ThemeHelper.INSTANCE
                        .value(StatCollector.translateToLocal("hud.msg.wdmla.hardness"), FormatUtil.STANDARD.format(hardness)));
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.HARDNESS;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }
}

package com.gtnewhorizons.wdmla.plugin.core;

import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;

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
                        .value(LangUtil.translateG("hud.msg.wdmla.hardness"), String.format("%.0f", hardness)));
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

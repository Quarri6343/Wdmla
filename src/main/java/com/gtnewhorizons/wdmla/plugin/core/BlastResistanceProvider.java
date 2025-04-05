package com.gtnewhorizons.wdmla.plugin.core;

import net.minecraft.util.ResourceLocation;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

import mcp.mobius.waila.cbcore.LangUtil;

public enum BlastResistanceProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        float blastResistance = accessor.getBlock().getExplosionResistance(null);
        tooltip.child(
                ThemeHelper.INSTANCE.value(
                        LangUtil.translateG("hud.msg.wdmla.blast_resistance"),
                        String.format("%.0f", blastResistance)));
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.BLAST_RESISTANCE;
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

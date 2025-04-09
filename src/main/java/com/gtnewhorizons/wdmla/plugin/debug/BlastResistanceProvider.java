package com.gtnewhorizons.wdmla.plugin.debug;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.util.FormatUtil;

public enum BlastResistanceProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        float blastResistance = accessor.getBlock().getExplosionResistance(null);
        tooltip.child(
                ThemeHelper.INSTANCE.value(
                        StatCollector.translateToLocal("hud.msg.wdmla.blast.resistance"),
                        FormatUtil.STANDARD.format(blastResistance)));
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

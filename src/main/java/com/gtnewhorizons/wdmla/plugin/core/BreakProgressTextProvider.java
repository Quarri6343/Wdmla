package com.gtnewhorizons.wdmla.plugin.core;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.Identifiers;
import com.gtnewhorizons.wdmla.api.TooltipPosition;
import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.config.General;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.util.FormatUtil;

public enum BreakProgressTextProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        float breakProgress = Minecraft.getMinecraft().playerController.curBlockDamageMP;
        if (breakProgress == 0 || General.breakProgress.mode != General.BreakProgress.Mode.TEXT) {
            return;
        }

        tooltip.child(
                ThemeHelper.INSTANCE.value(
                        StatCollector.translateToLocal("hud.msg.wdmla.progress"),
                        FormatUtil.PERCENTAGE_STANDARD.format(breakProgress)));
    }

    @Override
    public ResourceLocation getUid() {
        return Identifiers.BREAK_PROGRESS_TEXT;
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.DEFAULT_INFO + 2;
    }

    @Override
    public boolean isPriorityFixed() {
        return true;
    }

    @Override
    public boolean canToggleInGui() {
        return false;
    }
}

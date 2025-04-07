package com.gtnewhorizons.wdmla.plugin.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.gtnewhorizons.wdmla.api.BlockAccessor;
import com.gtnewhorizons.wdmla.api.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public enum BedProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        EntityPlayer player = accessor.getPlayer();

        if (player.isPlayerSleeping() || !player.isEntityAlive()) {
            appendCannotSleep(tooltip);
            return;
        }

        if (!accessor.getWorld().provider.isSurfaceWorld()) {
            appendCannotSleep(tooltip);
            return;
        }

        long time = accessor.getWorld().getWorldTime();
        if (time < 12541 || time > 23458) {
            appendCannotSleep(tooltip);
        }

        // we don't suggest player can sleep at this point because we don't check most of the conditions
    }

    private void appendCannotSleep(ITooltip tooltip) {
        tooltip.horizontal().text(String.format("%s: ", StatCollector.translateToLocal("hud.msg.wdmla.cansleep")))
                .child(ThemeHelper.INSTANCE.failure(StatCollector.translateToLocal("hud.msg.wdmla.no")));
    }

    @Override
    public ResourceLocation getUid() {
        return VanillaIdentifiers.BED;
    }

    @Override
    public boolean enabledByDefault() {
        return false;
    }
}

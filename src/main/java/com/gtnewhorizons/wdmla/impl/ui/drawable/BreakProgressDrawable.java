package com.gtnewhorizons.wdmla.impl.ui.drawable;

import com.gtnewhorizons.wdmla.config.General;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Area;
import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.ColorPalette;
import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.impl.ui.value.Alpha;
import com.gtnewhorizons.wdmla.impl.ui.value.HUDBlockDamage;
import com.gtnewhorizons.wdmla.overlay.GuiDraw;

public enum BreakProgressDrawable implements IDrawable {
    INSTANCE;

    private @NotNull Alpha progressAlpha = new Alpha(0);
    private static @NotNull HUDBlockDamage savedDamage = new HUDBlockDamage();
    public boolean isBlockBrokenRecently = false;


    @Override
    public void draw(IArea area) {
        if(General.breakProgress.mode != General.BreakProgress.Mode.FILLING_BAR) {
            return;
        }

        HUDBlockDamage damage = new HUDBlockDamage();
        if (damage.isIntact() && progressAlpha.isTransparent()) {
            return;
        }

        if (!damage.isIntact()) {
            savedDamage = damage;
            progressAlpha = savedDamage.getAlphaForProgress();
            isBlockBrokenRecently = false;
        } else {
            if (isBlockBrokenRecently) {
                savedDamage = new HUDBlockDamage(1);
                progressAlpha = savedDamage.getAlphaForProgress();
                isBlockBrokenRecently = false;
            }
            else {
                progressAlpha = progressAlpha.fade();
            }
        }

        int color = progressAlpha.apply(ColorPalette.BREAK_PROGRESS_DEFAULT); // TODO: change color with harvestability

        if (General.breakProgress.position == General.BreakProgress.Position.BOTTOM) {
            area = new Area(area.getX(), area.getEY(), area.getEX(), area.getEY());
        }
        GuiDraw.drawGradientRect(savedDamage.computeDrawArea(area), color, color);
    }
}

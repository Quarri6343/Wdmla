package com.gtnewhorizons.wdmla.impl.ui.drawable;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.impl.ui.value.Alpha;
import com.gtnewhorizons.wdmla.impl.ui.value.HUDBlockDamage;

public class BreakProgressDrawable implements IDrawable {

    private static final int DEFAULT_COLOR = 0xFFA0A0A0;
    private static final int FAILURE_COLOR = 0xFFAA0000;

    private static @NotNull Alpha progressAlpha = new Alpha(0);
    private static @NotNull HUDBlockDamage savedDamage = new HUDBlockDamage();

    @Override
    public void draw(IArea area) {
        HUDBlockDamage damage = new HUDBlockDamage();
        if (damage.isIntact() && progressAlpha.isTransparent()) {
            return;
        }

        if (!damage.isIntact()) {
            progressAlpha = damage.getAlphaForProgress();
            savedDamage = damage;
        } else {
            progressAlpha = progressAlpha.fade();
        }

        int color = progressAlpha.apply(DEFAULT_COLOR); // TODO: change color with harvestability

        GuiDraw.drawGradientRect(savedDamage.computeDrawArea(area), color, color);
    }
}

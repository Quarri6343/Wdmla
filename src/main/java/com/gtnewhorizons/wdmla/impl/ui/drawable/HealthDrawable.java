package com.gtnewhorizons.wdmla.impl.ui.drawable;

import net.minecraft.util.MathHelper;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.overlay.VanillaIconUI;

public class HealthDrawable implements IDrawable {

    private final int maxHeartsPerLine; // max hearts fit in line
    private final float health; // number of hearts
    private final float maxHealth; // number of max hearts

    public HealthDrawable(int maxHeartsPerLine, float health, float maxHealth) {
        this.maxHeartsPerLine = maxHeartsPerLine;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    @Override
    public void draw(IArea area) {
        int nHearts = MathHelper.ceiling_float_int(maxHealth);
        int heartsPerLine = (int) (Math.min(maxHeartsPerLine, Math.ceil(maxHealth)));

        int offsetX = area.getX();
        int offsetY = area.getY();

        for (int iheart = 1; iheart <= nHearts; iheart++) {

            if (iheart <= MathHelper.floor_float(health)) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaIconUI.HEART);
                offsetX += 8;
            }

            if ((iheart > health) && (iheart <= health + 0.5f)) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaIconUI.HHEART);
                offsetX += 8;
            }

            if (iheart > health + 0.5f) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaIconUI.EHEART);
                offsetX += 8;
            }

            if (iheart % heartsPerLine == 0) {
                offsetY += 10;
                offsetX = area.getX();
            }
        }
    }
}

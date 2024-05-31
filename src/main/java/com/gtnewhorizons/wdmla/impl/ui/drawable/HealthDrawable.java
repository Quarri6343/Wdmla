package com.gtnewhorizons.wdmla.impl.ui.drawable;

import net.minecraft.util.MathHelper;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.overlay.VanillaIconUI;

public class HealthDrawable implements IDrawable {

    // TODO: use float as arg
    private final String[] args;

    public HealthDrawable(String[] args) {
        this.args = args;
    }

    @Override
    public void draw(IArea area) {
        float maxHeartsPerLine = Float.parseFloat(args[0]); // max hearts fit in line
        float health = Float.parseFloat(args[1]); // number of hearts
        float maxhealth = Float.parseFloat(args[2]); // number of max hearts

        int nHearts = MathHelper.ceiling_float_int(maxhealth);
        int heartsPerLine = (int) (Math.min(maxHeartsPerLine, Math.ceil(maxhealth)));

        int offsetX = area.getX();
        int offsetY = area.getY();

        for (int iheart = 1; iheart <= nHearts; iheart++) {

            if (iheart <= MathHelper.floor_float(health)) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaIconUI.HEART);
                offsetX += 8;
            }

            if ((iheart > health) && (iheart < health + 1)) {
                GuiDraw.renderVanillaIcon(offsetX, offsetY, 8, 8, VanillaIconUI.HHEART);
                offsetX += 8;
            }

            if (iheart >= health + 1) {
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

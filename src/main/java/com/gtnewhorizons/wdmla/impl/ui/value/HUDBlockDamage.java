package com.gtnewhorizons.wdmla.impl.ui.value;

import net.minecraft.client.Minecraft;

import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;
import com.gtnewhorizons.wdmla.impl.ui.sizer.Area;

public class HUDBlockDamage {

    private final float damage;

    public HUDBlockDamage() {
        damage = Minecraft.getMinecraft().playerController.curBlockDamageMP;
    }

    public boolean isIntact() {
        return damage == 0;
    }

    /**
     * get corresponding alpha value of the current block damage for HUD
     */
    public Alpha getAlphaForProgress() {
        float alpha = Math.min(damage, 0.6F) + 0.4F * damage;
        return new Alpha(Math.min(alpha, 1));
    }

    public IArea computeDrawArea(IArea area) {
        int width = (int) ((area.getW() - 1) * damage);
        int totalWidth = width > 0 ? width - 1 : 0 ; //consider the edge of tooltip box
        return new Area(area.getX() + 1, area.getY(), totalWidth, 2); // TODO: configure Area via config
    }
}

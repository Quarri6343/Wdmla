package com.gtnewhorizons.wdmla.overlay;

import net.minecraft.util.IIcon;

public enum VanillaUIIcons implements IIcon {

    // Gui.icons
    HEART(52, 0, 9, 9),
    HEART_BG(52,9,9,9),
    HHEART(61, 0, 9, 9),
    EHEART(52, 9, 9, 9),
    ARMOR(34, 9, 9, 9),
    ARMOR_BG(16, 9, 9, 9),
    HARMOR(24, 9, 9, 9),
    EARMOR(16, 9, 9, 9),
    BUBBLEEXP(25, 18, 9, 9);

    /**
     * u, v, sizeU, sizeV
     */
    public final int u, v, su, sv;

    VanillaUIIcons(int u, int v, int su, int sv) {
        this.u = u;
        this.v = v;
        this.su = su;
        this.sv = sv;
    }

    @Override
    public int getIconWidth() {
        return su;
    }

    @Override
    public int getIconHeight() {
        return sv;
    }

    @Override
    public float getMinU() {
        return u;
    }

    @Override
    public float getMaxU() {
        return u + su;
    }

    @Override
    public float getInterpolatedU(double interpolation) {
        return (float)(u + su * interpolation);
    }

    @Override
    public float getMinV() {
        return v;
    }

    @Override
    public float getMaxV() {
        return v + sv;
    }

    @Override
    public float getInterpolatedV(double interpolation) {
        return (float)(v + sv * interpolation);
    }

    @Override
    public String getIconName() {
        return this.name();
    }
}

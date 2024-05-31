package com.gtnewhorizons.wdmla.overlay;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public enum VanillaIconUI {

    HEART(52, 0, 9, 9, 52, 9, 9, 9),
    HHEART(61, 0, 9, 9, 52, 9, 9, 9),
    EHEART(52, 9, 9, 9),
    BUBBLEEXP(25, 18, 9, 9);

    /**
     * u, v, sizeU, sizeV
     */
    public final int u, v, su, sv;
    /**
     * backgroundU, backgroundV, backgroundSizeU, backgroundSizeV
     */
    public final int bu, bv, bsu, bsv;

    public static final ResourceLocation PATH = Gui.icons;
    public static final int SIZE = 8;

    VanillaIconUI(int u, int v, int su, int sv) {
        this(u, v, su, sv, -1, -1, -1, -1);
    }

    VanillaIconUI(int u, int v, int su, int sv, int bu, int bv, int bsu, int bsv) {
        this.u = u;
        this.v = v;
        this.su = su;
        this.sv = sv;
        this.bu = bu;
        this.bv = bv;
        this.bsu = bsu;
        this.bsv = bsv;
    }
}

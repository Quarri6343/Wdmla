package com.gtnewhorizons.wdmla.api.ui.sizer;

/**
 * The two-dimensional position and size information.
 * 
 * @see java.awt.Point
 */
public interface IArea extends ISize {

    int getX();

    int getY();

    default int getEX() {
        return getX() + getW();
    }

    default int getEY() {
        return getY() + getH();
    }
}

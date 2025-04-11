package com.gtnewhorizons.wdmla.api.ui.style;

/**
 * collection of rectangle settings.
 */
public interface IRectStyle {

    /**
     * Note: getColor is reserved for any child component extend this.
     * 
     * @return the first gradient color of this rectangle
     */
    int getBackgroundColor1();

    /**
     * @return the second gradient color of this rectangle
     */
    int getBackgroundColor2();

    /**
     * @return the color which is applied to the edge of rectangle
     */
    int getBorderColor();
}

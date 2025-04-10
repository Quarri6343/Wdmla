package com.gtnewhorizons.wdmla.api.ui.style;

/**
 * Collection of filled bar settings.
 * {@inheritDoc}
 */
public interface IAmountStyle extends IRectStyle {

    /**
     * TODO:animated sprite instead of single color
     * @return the single color for partially filling the background rectangle.
     */
    int getFilledColor();

    /**
     * @return the color which is applied to the vertical indicator of the bar
     */
    int getAlternateFilledColor();
}

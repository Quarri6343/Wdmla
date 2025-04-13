package com.gtnewhorizons.wdmla.api.ui.style;

import com.gtnewhorizons.wdmla.api.ui.IDrawable;
import org.jetbrains.annotations.Nullable;

/**
 * Collection of filled bar settings.
 */
public interface IAmountStyle extends IRectStyle {

    /**
     * If this is not null, it will be rendered instead of filled color.<br>
     * It must have flexible size
     * @return the overlay drawable
     */
    @Nullable
    IDrawable getOverlay();

    /**
     * TODO:animated sprite instead of single color
     * 
     * @return the single color for partially filling the background rectangle.
     */
    int getFilledColor();

    /**
     * @return the color which is applied to the vertical indicator of the bar
     */
    int getAlternateFilledColor();
}

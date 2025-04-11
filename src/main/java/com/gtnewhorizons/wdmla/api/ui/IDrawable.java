package com.gtnewhorizons.wdmla.api.ui;

import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;

/**
 * This class will call rendering class to render the ui, based on provided {@link com.gtnewhorizons.wdmla.api.ui.sizer.IArea} information.<br>
 * Any additional information can be passed to its constructor.
 */
public interface IDrawable {

    /**
     * Main draw call.
     * @param area drawing location on ui
     */
    void draw(IArea area);
}

package com.gtnewhorizons.wdmla.api.ui;

import com.gtnewhorizons.wdmla.api.ui.sizer.IArea;

/**
 * This class will call rendering class to render the ui, based on provided style and {@link com.gtnewhorizons.wdmla.api.ui.sizer.IArea} information.
 */
public interface IDrawable {

    void draw(IArea area);
}

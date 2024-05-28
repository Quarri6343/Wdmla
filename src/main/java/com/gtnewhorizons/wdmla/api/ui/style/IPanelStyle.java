package com.gtnewhorizons.wdmla.api.ui.style;

import com.gtnewhorizons.wdmla.api.ui.ComponentAlignment;

public interface IPanelStyle {

    int NO_BORDER = 0x00000000;
    int DEFAULT_SPACE = 2;
    int DEFAULT_BORDER_THICKNESS = 2;

    int getBorderColor();

    int getBorderThickness();

    ComponentAlignment getAlignment();

    int getSpacing();
}

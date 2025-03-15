package com.gtnewhorizons.wdmla.api.ui.style;

import com.gtnewhorizons.wdmla.api.ui.ComponentAlignment;

public interface IPanelStyle {

    int DEFAULT_SPACE = 2;
    int DEFAULT_BORDER_THICKNESS = 2;

    int getBorderColor();

    int getBorderThickness();

    ComponentAlignment getAlignment();

    int getSpacing();
}

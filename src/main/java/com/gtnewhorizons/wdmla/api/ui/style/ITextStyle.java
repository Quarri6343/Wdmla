package com.gtnewhorizons.wdmla.api.ui.style;

import com.gtnewhorizons.wdmla.api.ui.ComponentAlignment;

public interface ITextStyle {

    ComponentAlignment DEFAULT_ALIGN = ComponentAlignment.TOPLEFT;
    int DEFAULT_COLOR = 0xA0A0A0;
    boolean DEFAULT_SHADOW = true;

    ComponentAlignment getAlignment();

    int getColor();

    boolean getShadow();
}

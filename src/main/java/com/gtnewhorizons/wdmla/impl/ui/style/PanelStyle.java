package com.gtnewhorizons.wdmla.impl.ui.style;

import com.gtnewhorizons.wdmla.api.ui.ComponentAlignment;
import com.gtnewhorizons.wdmla.api.ui.style.IPanelStyle;

public class PanelStyle implements IPanelStyle {

    private int borderColor;
    private ComponentAlignment alignment;
    private int spacing;

    public PanelStyle() {
        this.borderColor = NO_BORDER;
        this.alignment = ComponentAlignment.TOPLEFT;
        this.spacing = DEFAULT_SPACE;
    }

    public PanelStyle borderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public PanelStyle alignment(ComponentAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public PanelStyle spacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public int getBorderThickness() {
        if (borderColor == NO_BORDER) {
            return 0;
        }

        return DEFAULT_BORDER_THICKNESS;
    }

    @Override
    public ComponentAlignment getAlignment() {
        return alignment;
    }

    @Override
    public int getSpacing() {
        return spacing;
    }
}

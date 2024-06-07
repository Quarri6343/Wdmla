package com.gtnewhorizons.wdmla.impl.ui.style;

import com.gtnewhorizons.wdmla.api.ui.style.IItemStyle;

/**
 * reserved class for further customization
 */
public class ItemStyle implements IItemStyle {

    private boolean drawOverlay;

    public ItemStyle() {
        this.drawOverlay = true;
    }

    public ItemStyle drawOverlay(boolean drawOverlay) {
        this.drawOverlay = drawOverlay;
        return this;
    }

    @Override
    public boolean getDrawOverlay() {
        return drawOverlay;
    }
}

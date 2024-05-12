package mcp.mobius.wdmla.api;

import mcp.mobius.waila.api.elements.ElementAlignment;

public interface ITextStyle {

    ElementAlignment DEFAULT_ALIGN = ElementAlignment.ALIGN_TOPLEFT;
    int DEFAULT_COLOR = 0xDEDEDE;
    boolean DEFAULT_SHADOW = true;

    ElementAlignment getAlignment();

    int getColor();

    boolean getShadow();
}

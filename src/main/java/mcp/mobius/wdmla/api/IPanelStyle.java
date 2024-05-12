package mcp.mobius.wdmla.api;

import mcp.mobius.waila.api.elements.ElementAlignment;

public interface IPanelStyle {

    int NO_BORDER = 0x00000000;
    int DEFAULT_SPACE = 2;

    int getBorderColor();

    ElementAlignment getAlignment();

    int getSpacing();
}

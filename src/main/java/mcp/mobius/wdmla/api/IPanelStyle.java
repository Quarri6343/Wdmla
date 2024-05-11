package mcp.mobius.wdmla.api;

import mcp.mobius.waila.api.elements.ElementAlignment;

public interface IPanelStyle {

    int NO_BORDER = 0x00000000;

    IPanelStyle borderColor(int c);

    IPanelStyle alignment(ElementAlignment align);

    int getBorderColor();

    ElementAlignment getAlignment();
}
